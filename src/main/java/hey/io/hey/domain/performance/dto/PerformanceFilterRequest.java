package hey.io.hey.domain.performance.dto;

import hey.io.hey.domain.performance.domain.enums.PerformanceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "공연 리스트 필터 Request")
public class PerformanceFilterRequest {

    @Schema(description = "공연 상태 (UPCOMING / ONGOING / COMPLETED")
    private List<PerformanceStatus> statuses = new ArrayList<>();

    @Schema(description = "내한 여부")
    private String visit;

}
