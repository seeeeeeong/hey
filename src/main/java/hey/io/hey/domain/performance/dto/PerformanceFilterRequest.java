package hey.io.hey.domain.performance.dto;

import hey.io.hey.domain.performance.domain.enums.PerformanceStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceFilterRequest {

    private List<PerformanceStatus> statuses = new ArrayList<>();

}
