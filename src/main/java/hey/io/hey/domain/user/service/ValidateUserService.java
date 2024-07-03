package hey.io.hey.domain.user.service;

import hey.io.hey.domain.user.domain.SocialCode;
import hey.io.hey.domain.user.domain.User;
import hey.io.hey.domain.user.exception.UserAccessDeniedException;
import hey.io.hey.domain.user.exception.UserNotFoundException;
import hey.io.hey.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ValidateUserService {

    private final UserRepository userRepository;

    public User validateUserById(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public User validateRegisteredUserByEmail(String email, SocialCode socialCode) {
        return userRepository.findAllByEmail(email).stream()
                .filter(user -> user.getSocialCode() == socialCode)
                .findFirst()
                .orElse(null);
    }

    public User validateAdminUserById(Long userId) {
        User user = validateUserById(userId);
        if (user.isAdmin()) {
            return user;
        } else {
            throw new UserAccessDeniedException();
        }
    }
}
