package hey.io.hey.domain.user.service;

import hey.io.hey.common.exception.BusinessException;
import hey.io.hey.common.exception.ErrorCode;
import hey.io.hey.common.security.jwt.JwtTokenProvider;
import hey.io.hey.domain.user.domain.User;
import hey.io.hey.common.security.jwt.dto.JwtTokenResponse;
import hey.io.hey.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void join(String email, String password) {
        userRepository.save(User.create(email, password));
    }

    @Transactional
    public JwtTokenResponse login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        String jwtAccessToken = jwtTokenProvider.createAccessToken(user.getUserId(), user.getUserRole());
        String jwtRefreshToken = jwtTokenProvider.createRefreshToken(user.getUserId(), user.getUserRole());

        return JwtTokenResponse.of(jwtAccessToken, jwtRefreshToken);
    }

}
