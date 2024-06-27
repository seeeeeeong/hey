package hey.io.hey.domain.follow.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowResponse {

    private String performanceId;
    private String message;

    public FollowResponse(String performanceId, String message) {
        this.performanceId = performanceId;
        this.message = message;
    }

}
