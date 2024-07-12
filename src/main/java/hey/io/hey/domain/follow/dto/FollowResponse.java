package hey.io.hey.domain.follow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "팔로우 Response")
public class FollowResponse {

    @Schema(description = "팔로우 공연 / 아티스트 아이디")
    private String id;

    @Schema(description = "팔로우 메시지")
    private String message;

    public FollowResponse(String id, String message) {
        this.id = id;
        this.message = message;
    }

}
