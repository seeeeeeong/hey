package hey.io.hey.domain.performance.controller;

import hey.io.hey.common.resolver.AuthUser;
import hey.io.hey.common.response.SliceResponse;
import hey.io.hey.common.response.SuccessResponse;
import hey.io.hey.common.security.jwt.JwtTokenInfo;
import hey.io.hey.domain.artist.dto.ArtistListResponse;
import hey.io.hey.domain.performance.dto.*;
import hey.io.hey.domain.performance.service.PerformanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get Performance List", description = "Get Performance List API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200")
    })
    @GetMapping
    public ResponseEntity<SuccessResponse<SliceResponse<PerformanceResponse>>> getPerformancesByCondition(PerformanceFilterRequest request,
                                                                                                          @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                                                                                          @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                                                          @RequestParam(name = "direction", required = false, defaultValue = "DESC") Direction direction) {
        return SuccessResponse.of(performanceService.getPerformancesByCondition(request, size, page, direction)).asHttp(HttpStatus.OK);
    }

    @Operation(summary = "Search Performance List", description = "Search Performance List API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200")
    })
    @GetMapping("/search")
    public ResponseEntity<SuccessResponse<SliceResponse<PerformanceResponse>>> searchPerformances(@Valid @RequestBody PerformanceSearchRequest request,
                                                                                                  @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                                                                                  @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                                                  @RequestParam(name = "direction", required = false, defaultValue = "DESC") Direction direction) {
        return SuccessResponse.of(performanceService.searchPerformances(request, size, page, direction)).asHttp(HttpStatus.OK);
    }

    @Operation(summary = "Get New Performance List", description = "Get New Performance List API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200")
    })
    @GetMapping("/new")
    public ResponseEntity<SuccessResponse<List<PerformanceResponse>>> getNewPerformances() {
        return SuccessResponse.of(performanceService.getNewPerformances()).asHttp(HttpStatus.OK);
    }

    @Operation(summary = "Get Performance Rank", description = "Get Performance Rank API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200")
    })
    @GetMapping("/rank")
    public ResponseEntity<SuccessResponse<List<PerformanceResponse>>> getBoxOffice(@RequestBody BoxOfficeRankRequest request) {
        return SuccessResponse.of(performanceService.getBoxOfficeRank(request)).asHttp(HttpStatus.OK);
    }

    @Operation(summary = "Get Performance Detail", description = "Get Performance Detail API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200"),
            @ApiResponse(
                    responseCode = "404",
                    description = "P001 : 공연을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<PerformanceDetailResponse>> getPerformance(@PathVariable("id") String performanceId) {
        return SuccessResponse.of(performanceService.getPerformance(performanceId)).asHttp(HttpStatus.OK);
    }

    @Operation(summary = "Get Performance's Artists", description = "Get Performance's Artists API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200"),
            @ApiResponse(
                    responseCode = "404",
                    description = "P001 : 공연을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/{id}/artists")
    public ResponseEntity<SuccessResponse<List<ArtistListResponse>>> getPerformanceArtists(@PathVariable("id") String performanceId) {
        return SuccessResponse.of(performanceService.getPerformanceArtists(performanceId)).asHttp(HttpStatus.OK);
    }

}
