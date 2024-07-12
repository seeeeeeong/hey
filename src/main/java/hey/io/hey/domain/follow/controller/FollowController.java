package hey.io.hey.domain.follow.controller;


import hey.io.hey.common.resolver.AuthUser;
import hey.io.hey.common.response.SliceResponse;
import hey.io.hey.common.response.SuccessResponse;
import hey.io.hey.common.security.jwt.JwtTokenInfo;
import hey.io.hey.domain.artist.dto.ArtistListResponse;
import hey.io.hey.domain.follow.dto.DeleteFollowRequest;
import hey.io.hey.domain.follow.dto.FollowResponse;
import hey.io.hey.domain.follow.service.FollowService;
import hey.io.hey.domain.performance.dto.PerformanceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @Operation(summary = "Follow Performance", description = "Follow Performance API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200"),
            @ApiResponse(
                    responseCode = "404",
                    description = "U001 : 회원을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(
                    responseCode = "404",
                    description = "P001: 공연을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(hidden = true))),
    })
    @PostMapping("/follow/performances/{id}")
    public ResponseEntity<SuccessResponse<FollowResponse>> followPerformance(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                                             @PathVariable("id") String performanceId) {
        return SuccessResponse.of(followService.followPerformance(jwtTokenInfo.getUserId(), performanceId)).asHttp(HttpStatus.OK);
    }

    @Operation(summary = "Get Followed Performances", description = "Get Followed Performances API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200"),
            @ApiResponse(
                    responseCode = "404",
                    description = "U001 : 회원을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/follow/performances")
    public ResponseEntity<SuccessResponse<SliceResponse<PerformanceResponse>>> getFollowPerformances(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                                                                     @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                                                                                     @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                                                     @RequestParam(name = "direction", required = false, defaultValue = "DESC") Direction direction) {
        return SuccessResponse.of(followService.getFollowPerformances(jwtTokenInfo.getUserId(), size, page, direction)).asHttp(HttpStatus.OK);
    }

    @Operation(summary = "Delete Followed Performances", description = "Delete Followed Performances API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200"),
            @ApiResponse(
                    responseCode = "404",
                    description = "U001 : 회원을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(
                    responseCode = "404",
                    description = "P001: 공연을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(hidden = true))),
    })
    @DeleteMapping("/follow/performances")
    public ResponseEntity<SuccessResponse<Void>> deleteFollowPerformances(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                         @RequestBody DeleteFollowRequest deleteFollowRequest) {
        followService.deleteFollowPerformances(jwtTokenInfo.getUserId(), deleteFollowRequest.getIds());
        SuccessResponse<Void> response = SuccessResponse.of(null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Follow Artist", description = "Follow Artist API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200"),
            @ApiResponse(
                    responseCode = "404",
                    description = "U001 : 회원을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(
                    responseCode = "404",
                    description = "AR001: 아티스트를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(hidden = true))),
    })
    @PostMapping("/follow/artists/{id}")
    public ResponseEntity<SuccessResponse<FollowResponse>> followArtists(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                                         @PathVariable("id") String artistId) {
        return SuccessResponse.of(followService.followArtist(jwtTokenInfo.getUserId(), artistId)).asHttp(HttpStatus.OK);
    }

    @Operation(summary = "Get Followed Artists", description = "Get Followed Artists API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200"),
            @ApiResponse(
                    responseCode = "404",
                    description = "U001 : 회원을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/follow/artists")
    public ResponseEntity<SuccessResponse<SliceResponse<ArtistListResponse>>> getFollowArtists(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                                                               @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                                                                               @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                                               @RequestParam(name = "direction", required = false, defaultValue = "DESC") Direction direction) {
        return SuccessResponse.of(followService.getFollowArtists(jwtTokenInfo.getUserId(), size, page, direction)).asHttp(HttpStatus.OK);
    }

    @Operation(summary = "Delete Followed Artists", description = "Delete Followed Artists API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200"),
            @ApiResponse(
                    responseCode = "404",
                    description = "U001 : 회원을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(
                    responseCode = "404",
                    description = "AR001: 아티스트를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(hidden = true))),
    })
    @DeleteMapping("/follow/artists")
    public ResponseEntity<SuccessResponse<Void>> deleteFollowArtists(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                    @RequestBody DeleteFollowRequest deleteFollowRequest) {
        followService.deleteFollowArtists(jwtTokenInfo.getUserId(), deleteFollowRequest.getIds());
        SuccessResponse<Void> response = SuccessResponse.of(null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
