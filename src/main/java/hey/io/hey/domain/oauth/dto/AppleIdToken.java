package hey.io.hey.domain.oauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
@Schema(description = "AppleIdToken DTO")
public class AppleIdToken {


    @Schema(description = "발행자")
    private String iss;

    @Schema(description = "대상자")
    private String aud;

    @Schema(description = "만료 시간")
    private Long exp;

    @Schema(description = "발행 시간")
    private Long iat;

    @Schema(description = "주체")
    private String sub;

    @Schema(description = "난수")
    private String nonce;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "이메일 검증 여부")
    private Boolean emailVerified;

    @Schema(description = "수신자 목록")
    private String[] audList;

    @Schema(description = "인증 시간")
    private Integer authTime;

    @Schema(description = "난수 지원 여부")
    private String nonceSupported;

}
