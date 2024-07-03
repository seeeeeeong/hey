package hey.io.hey.domain.oauth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseAccessToken {

    @NotBlank
    private final String accessToken;

    public static ResponseAccessToken of(String accessToken) {
        return new ResponseAccessToken(accessToken);
    }

}
