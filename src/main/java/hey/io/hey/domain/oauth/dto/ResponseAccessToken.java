package hey.io.hey.domain.oauth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "AccessToken Response")
public class ResponseAccessToken {

    @NotBlank
    @Schema(description = "AccessToken")
    private final String accessToken;

    public static ResponseAccessToken of(String accessToken) {
        return new ResponseAccessToken(accessToken);
    }

}
