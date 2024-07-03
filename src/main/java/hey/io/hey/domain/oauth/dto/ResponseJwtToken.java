package hey.io.hey.domain.oauth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseJwtToken {

    @NotBlank
    private final String accessToken;

    @NotBlank
    private final String refreshToken;

    public static ResponseJwtToken of(String accessToken, String refreshToken) {
        return new ResponseJwtToken(accessToken, refreshToken);
    }

}
