package hey.io.hey.domain.performance.controller;

import hey.io.hey.common.response.SliceResponse;
import hey.io.hey.common.response.SuccessResponse;
import hey.io.hey.domain.performance.dto.CreatePerformanceRequest;
import hey.io.hey.domain.performance.dto.PerformanceFilterRequest;
import hey.io.hey.domain.performance.dto.PerformanceResponse;
import hey.io.hey.domain.performance.dto.PerformanceSearchRequest;
import hey.io.hey.domain.performance.service.PerformanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/search")
    public ResponseEntity<SuccessResponse<SliceResponse<PerformanceResponse>>> searchPerformances(@Valid @RequestBody PerformanceSearchRequest request,
                                                                                                  @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                                                                                  @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                                                  @RequestParam(name = "direction", required = false, defaultValue = "DESC") Direction direction) {
        return SuccessResponse.of(performanceService.searchPerformances(request, size, page, direction)).asHttp(HttpStatus.OK);
    }

    @GetMapping("/new")
    public ResponseEntity<SuccessResponse<List<PerformanceResponse>>> getNewPerformances() {
        return SuccessResponse.of(performanceService.getNewPerformances()).asHttp(HttpStatus.OK);
    }
}