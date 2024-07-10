package hey.io.hey.domain.follow.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowResponse {

    private String id;
    private String message;

    public FollowResponse(String id, String message) {
        this.id = id;
        this.message = message;
    }

}
