package hey.io.hey.domain.report.controller;

import hey.io.hey.common.resolver.AuthUser;
import hey.io.hey.common.response.SuccessResponse;
import hey.io.hey.common.security.jwt.JwtTokenInfo;
import hey.io.hey.domain.report.dto.ReportRequest;
import hey.io.hey.domain.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/performances/{id}/report")
    public ResponseEntity<Void> reportPerformance(@PathVariable("id") String performanceId,
                                                                   @AuthUser JwtTokenInfo jwtTokenInfo,
                                                                   @RequestBody ReportRequest request) {
        reportService.reportPerformance(performanceId, jwtTokenInfo.getUserId(), request);
        return ResponseEntity.noContent().build();
    }

}
