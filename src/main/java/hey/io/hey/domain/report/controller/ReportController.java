package hey.io.hey.domain.report.controller;

import hey.io.hey.common.resolver.AuthUser;
import hey.io.hey.common.response.SuccessResponse;
import hey.io.hey.common.security.jwt.JwtTokenInfo;
import hey.io.hey.domain.report.dto.ReportRequest;
import hey.io.hey.domain.report.dto.ReportResponse;
import hey.io.hey.domain.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Report Performance", description = "Report Performance")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200"),
            @ApiResponse(
                    responseCode = "404",
                    description = "P001 : 공연을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(
                    responseCode = "404",
                    description = "U001 : 유저를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(hidden = true))),
    })
    @PostMapping("/performances/{id}/report")
    public ResponseEntity<SuccessResponse<ReportResponse>> reportPerformance(@PathVariable("id") String performanceId,
                                                                             @AuthUser @Parameter(hidden = true) JwtTokenInfo jwtTokenInfo,
                                                                             @RequestBody ReportRequest request) {
        return SuccessResponse.of(reportService.reportPerformance(performanceId, jwtTokenInfo.getUserId(), request)).asHttp(HttpStatus.OK);
    }

    @Operation(summary = "Report Artist", description = "Report Artist")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200"),
            @ApiResponse(
                    responseCode = "404",
                    description = "AR001 : 아티스트를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(
                    responseCode = "404",
                    description = "U001 : 유저를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(hidden = true))),
    })
    @PostMapping("/artists/{id}/report")
    public ResponseEntity<SuccessResponse<ReportResponse>> reportArtist(@PathVariable("id") String artistId,
                                                                         @AuthUser @Parameter(hidden = true) JwtTokenInfo jwtTokenInfo,
                                                                         @RequestBody ReportRequest request) {
        return SuccessResponse.of(reportService.reportArtist(artistId, jwtTokenInfo.getUserId(), request)).asHttp(HttpStatus.OK);
    }

}
