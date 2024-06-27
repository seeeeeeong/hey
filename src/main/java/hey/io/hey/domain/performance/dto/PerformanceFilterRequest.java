package hey.io.hey.domain.performance.dto;

import hey.io.hey.domain.performance.domain.enums.PerformanceStatus;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceFilterRequest {

    private List<PerformanceStatus> statuses = new ArrayList<>();

}
