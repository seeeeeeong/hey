package hey.io.hey.domain.performance.controller;

import hey.io.hey.common.response.SliceResponse;
import hey.io.hey.common.response.SuccessResponse;
import hey.io.hey.domain.performance.dto.CreatePerformanceRequest;
import hey.io.hey.domain.performance.dto.PerformanceFilterRequest;
import hey.io.hey.domain.performance.dto.PerformanceResponse;
import hey.io.hey.domain.performance.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/performances")
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;

    @PostMapping
    public ResponseEntity<SuccessResponse<Void>> createPerformance(@RequestBody CreatePerformanceRequest request) {
        performanceService.createPerformance(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<SliceResponse<PerformanceResponse>>> getPerformancesByCondition(@RequestBody PerformanceFilterRequest request,
                                                                                                          @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                                                                                          @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                                                          @RequestParam(name = "direction", required = false, defaultValue = "DESC") Direction direction) {
        return SuccessResponse.of(performanceService.getPerformancesByCondition(request, size, page, direction)).asHttp(HttpStatus.OK);
    }
}
