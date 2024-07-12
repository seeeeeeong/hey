package hey.io.hey.domain.performance.dto;

import hey.io.hey.domain.performance.domain.enums.PerformanceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "공연 검색 Request")
public class PerformanceSearchRequest {

    @NotBlank
    @Schema(description = "키워드")
    private String keyword;

    @Schema(description = "공연 상태 (UPCOMING / ONGOING / COMPLETED")
    private List<PerformanceStatus> statuses = new ArrayList<>();

}
