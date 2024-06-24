package hey.io.hey.domain.user.service;

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

    @Transactional
    public void join(String email, String password) {
        userRepository.save(User.create(email, password));
    }
}
