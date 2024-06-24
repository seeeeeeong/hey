package hey.io.hey.domain.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJoinRequest {

    private String email;
    private String password;

}
