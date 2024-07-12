package hey.io.hey.domain.oauth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "JwtToken Response")
public class ResponseJwtToken {

    @NotBlank
    @Schema(description = "Access Token")
    private final String accessToken;

    @NotBlank
    @Schema(description = "Refresh Token")
    private final String refreshToken;

    public static ResponseJwtToken of(String accessToken, String refreshToken) {
        return new ResponseJwtToken(accessToken, refreshToken);
    }

}
