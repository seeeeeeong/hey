package hey.io.hey.domain.user.controller;

import hey.io.hey.common.response.SuccessResponse;
import hey.io.hey.common.security.jwt.dto.JwtTokenResponse;
import hey.io.hey.domain.user.dto.UserJoinRequest;
import hey.io.hey.domain.user.dto.UserLoginRequest;
import hey.io.hey.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody UserJoinRequest request) {
        userService.join(request.getEmail(), request.getPassword());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<JwtTokenResponse>> login(@RequestBody UserLoginRequest request) {
        return SuccessResponse.of(userService.login(request.getEmail(), request.getPassword())).asHttp(HttpStatus.OK);
    }

}
