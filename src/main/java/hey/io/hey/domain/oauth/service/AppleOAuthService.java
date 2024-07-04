package hey.io.hey.domain.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hey.io.hey.common.exception.BusinessException;
import hey.io.hey.common.security.jwt.JwtTokenProvider;
import hey.io.hey.common.utils.HeaderUtils;
import hey.io.hey.domain.oauth.domain.Auth;
import hey.io.hey.domain.oauth.dto.AppleIdToken;
import hey.io.hey.domain.oauth.dto.OAuthAccessToken;
import hey.io.hey.domain.oauth.dto.RequestAppleLogin;
import hey.io.hey.domain.oauth.dto.ResponseJwtToken;
import hey.io.hey.domain.oauth.exception.OAuthNotFoundException;
import hey.io.hey.domain.oauth.properties.AppleProperties;
import hey.io.hey.domain.oauth.repository.AuthRepository;
import hey.io.hey.domain.user.domain.SocialCode;
import hey.io.hey.domain.user.domain.User;
import hey.io.hey.domain.user.service.UserService;
import hey.io.hey.domain.user.service.ValidateUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;

import static hey.io.hey.common.exception.ErrorCode.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AppleOAuthService {

    private final AppleProperties properties;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ValidateUserService validateUserService;
    private final UserService userService;
    private final AuthRepository authRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public ResponseJwtToken login(HttpServletRequest request, RequestAppleLogin requestAppleLogin) {
        OAuthAccessToken oAuthAccessToken = getAccessToken(request);
        AppleIdToken appleIdToken = getAppleIdToken(requestAppleLogin.getIdToken());
        log.info("[Apple User Login] appleIdToken: {}", appleIdToken);

        if (appleIdToken.getEmail() == null) {
            throw new BusinessException(APPLE_EMAIL_NOT_FOUND);
        }

        User user = validateUserService.validateRegisteredUserByEmail(
                appleIdToken.getEmail(), SocialCode.APPLE);
        if (user == null) {
            user = userService.registerAppleUser(appleIdToken, oAuthAccessToken.getRefreshToken());
        }
        Auth auth = authRepository.findByUser(user)
                .orElseThrow(OAuthNotFoundException::new);
        auth.update(appleIdToken.toString(), oAuthAccessToken.getRefreshToken());

        String jwtAccessToken = jwtTokenProvider.createAccessToken(user.getUserId(),
                user.getUserRole());
        String jwtRefreshToken = jwtTokenProvider.createRefreshToken(user.getUserId(),
                user.getUserRole());

        return ResponseJwtToken.of(jwtAccessToken, jwtRefreshToken);
    }

    @Transactional
    public void deleteAccount(User user) {
        Auth auth = authRepository.findByUser(user).orElseThrow(OAuthNotFoundException::new);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", properties.getClientId());
        body.add("client_secret", properties.getClientSecret());
        body.add("token", auth.getRefreshToken());
        body.add("token_type_hint", "refresh_token");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        String url = properties.getDeleteAccountUrl();
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new BusinessException(OAUTH_SERVER_FAILED, new Throwable(response.getBody()));
        }

        user.deleteUser();
    }

    private OAuthAccessToken getAccessToken(HttpServletRequest request) {
        String authorizationCode = HeaderUtils.getAuthCode(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", authorizationCode);
        body.add("client_id", properties.getClientId());
        body.add("client_secret", properties.getClientSecret());
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                properties.getTokenUrl(), entity, String.class);

        try {
            return objectMapper.readValue(response.getBody(), OAuthAccessToken.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(PARSING_ERROR, e);
        }
    }

    private AppleIdToken getAppleIdToken(String idToken) {
        String[] jwtParts = idToken.split("\\.");
        byte[] bytes = Base64.getDecoder().decode(jwtParts[1].getBytes());
        try {
            return objectMapper.readValue(bytes, AppleIdToken.class);
        } catch (IOException e) {
            throw new BusinessException(PARSING_ERROR, e);
        }
    }}
