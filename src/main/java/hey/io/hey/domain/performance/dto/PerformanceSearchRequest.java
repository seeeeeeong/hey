package hey.io.hey.domain.performance.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceSearchRequest {

    @NotBlank
    private String keyword;

}
