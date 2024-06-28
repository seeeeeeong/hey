package hey.io.hey.domain.performance.controller;

import hey.io.hey.common.resolver.AuthUser;
import hey.io.hey.common.response.SliceResponse;
import hey.io.hey.common.response.SuccessResponse;
import hey.io.hey.common.security.jwt.JwtTokenInfo;
import hey.io.hey.domain.performance.dto.*;
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

    @GetMapping
    public ResponseEntity<SuccessResponse<SliceResponse<PerformanceResponse>>> getPerformancesByCondition(PerformanceFilterRequest request,
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

    @GetMapping("/rank")
    public ResponseEntity<SuccessResponse<List<PerformanceResponse>>> getBoxOffice(@RequestBody BoxOfficeRankRequest request) {
        return SuccessResponse.of(performanceService.getBoxOfficeRank(request)).asHttp(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<PerformanceDetailResponse>> getPerformance(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                                                     @PathVariable("id") String performanceId) {
        return SuccessResponse.of(performanceService.getPerformance(performanceId)).asHttp(HttpStatus.OK);
    }
}
