package hey.io.hey.domain.performance.controller;

import hey.io.hey.common.response.SuccessResponse;
import hey.io.hey.domain.performance.dto.CreatePerformanceRequest;
import hey.io.hey.domain.performance.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
