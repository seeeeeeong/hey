package hey.io.hey.domain.follow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "팔로우 삭제 Request")
public class DeleteFollowRequest {

    @Schema(description = "팔로우 ID 목록")
    private List<String> ids;

}
