package hey.io.hey.domain.performance.dto;

import hey.io.hey.domain.performance.domain.enums.TimePeriod;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoxOfficeRankRequest {
    private TimePeriod timePeriod;
}
