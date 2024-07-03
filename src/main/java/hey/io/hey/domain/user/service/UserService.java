package hey.io.hey.domain.user.service;

import hey.io.hey.common.exception.BusinessException;
import hey.io.hey.common.exception.ErrorCode;
import hey.io.hey.common.security.jwt.JwtTokenProvider;
import hey.io.hey.common.security.jwt.dto.JwtTokenResponse;
import hey.io.hey.domain.oauth.domain.Auth;
import hey.io.hey.domain.oauth.repository.AuthRepository;
import hey.io.hey.domain.user.domain.SocialCode;
import hey.io.hey.domain.user.domain.User;
import hey.io.hey.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    @Transactional
    public User registerGoogleUser(String email, SocialCode socialCode, String refreshToken) {
        User user = userRepository.save(User.create(email, socialCode));
        Auth auth = Auth.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();
        authRepository.save(auth);
        return user;
    }
}
