package hey.io.hey.domain.oauth.controller;


import hey.io.hey.common.resolver.AuthUser;
import hey.io.hey.common.response.SuccessResponse;
import hey.io.hey.common.security.jwt.JwtTokenInfo;
import hey.io.hey.common.security.jwt.JwtTokenProvider;
import hey.io.hey.common.security.jwt.JwtType;
import hey.io.hey.common.utils.HeaderUtils;
import hey.io.hey.domain.oauth.dto.ResponseAccessToken;
import hey.io.hey.domain.oauth.dto.ResponseJwtToken;
import hey.io.hey.domain.oauth.service.GoogleOAuthService;
import hey.io.hey.domain.oauth.service.OAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class AuthController {

    private final OAuthService oAuthService;
    private final GoogleOAuthService googleOAuthService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/google/login")
    public ResponseEntity<SuccessResponse<ResponseJwtToken>> googleLogin(HttpServletRequest request) {
        return SuccessResponse.of(googleOAuthService.login(request)).asHttp(HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<SuccessResponse<ResponseAccessToken>> getAccessToken(HttpServletRequest request) {
        String refreshToken = HeaderUtils.getJwtToken(request, JwtType.REFRESH);
        String accessToken = jwtTokenProvider.createNewAccessTokenFromRefreshToken(refreshToken);
        return SuccessResponse.of(ResponseAccessToken.of(accessToken)).asHttp(HttpStatus.OK);
    }

    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteAccount(@AuthUser JwtTokenInfo tokenInfo) {
        oAuthService.deleteAccount(tokenInfo.getUserId());
        return ResponseEntity.noContent().build();
    }

}
