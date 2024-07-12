package hey.io.hey.domain.oauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "OAuth Access Token DTO")
public class OAuthAccessToken {

    @Schema(description = "Access Token")
    private String accessToken;

    @Schema(description = "기간")
    private int expiresIn;

    @Schema(description = "Refresh Token")
    private String refreshToken;

    @Schema(description = "토큰 타입")
    private String tokenType;

}
