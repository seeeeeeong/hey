package hey.io.hey.domain.fcm.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRequestDTO {
    private String targetToken;
    private String title;
    private String body;
    private String topic;
}
