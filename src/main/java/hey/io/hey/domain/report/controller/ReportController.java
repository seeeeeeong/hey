package hey.io.hey.domain.report.controller;

import hey.io.hey.common.resolver.AuthUser;
import hey.io.hey.common.response.SuccessResponse;
import hey.io.hey.common.security.jwt.JwtTokenInfo;
import hey.io.hey.domain.report.dto.ReportRequest;
import hey.io.hey.domain.report.dto.ReportResponse;
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
    public ResponseEntity<SuccessResponse<ReportResponse>> reportPerformance(@PathVariable("id") String performanceId,
                                                                             @AuthUser JwtTokenInfo jwtTokenInfo,
                                                                             @RequestBody ReportRequest request) {
        return SuccessResponse.of(reportService.reportPerformance(performanceId, jwtTokenInfo.getUserId(), request)).asHttp(HttpStatus.OK);
    }

    @PostMapping("/artists/{id}/report")
    public ResponseEntity<SuccessResponse<ReportResponse>> reportArtist(@PathVariable("id") String artistId,
                                                                         @AuthUser JwtTokenInfo jwtTokenInfo,
                                                                         @RequestBody ReportRequest request) {
        return SuccessResponse.of(reportService.reportArtist(artistId, jwtTokenInfo.getUserId(), request)).asHttp(HttpStatus.OK);
    }

}
