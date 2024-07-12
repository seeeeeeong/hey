package hey.io.hey.domain.fcm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "FCM Request")
public class MessageRequestDTO {


    @Schema(description = "Target Token")
    private String targetToken;

    @Schema(description = "메시지 타이틀")
    private String title;

    @Schema(description = "메시지 내용")
    private String body;

    @Schema(description = "메시지 Topic")
    private String topic;
}
