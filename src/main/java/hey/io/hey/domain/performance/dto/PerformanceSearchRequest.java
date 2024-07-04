package hey.io.hey.domain.performance.dto;

import hey.io.hey.domain.performance.domain.enums.PerformanceStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceSearchRequest {

    @NotBlank
    private String keyword;
    private List<PerformanceStatus> statuses = new ArrayList<>();

}
