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

    @PostMapping("/follow/performances/{id}")
    public ResponseEntity<SuccessResponse<FollowResponse>> followPerformance(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                                             @PathVariable("id") String performanceId) {
        return SuccessResponse.of(followService.followPerformance(jwtTokenInfo.getUserId(), performanceId)).asHttp(HttpStatus.OK);
    }

    @GetMapping("/follow/performances")
    public ResponseEntity<SuccessResponse<SliceResponse<PerformanceResponse>>> getFollowPerformances(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                                                                     @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                                                                                     @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                                                     @RequestParam(name = "direction", required = false, defaultValue = "DESC") Direction direction) {
        return SuccessResponse.of(followService.getFollowPerformances(jwtTokenInfo.getUserId(), size, page, direction)).asHttp(HttpStatus.OK);
    }

    @DeleteMapping("/follow/performances")
    public ResponseEntity<Void> deleteFollowPerformances(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                         @RequestBody DeleteFollowRequest deleteFollowRequest) {
        followService.deleteFollowPerformances(jwtTokenInfo.getUserId(), deleteFollowRequest.getIds());
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/follow/artists/{id}")
    public ResponseEntity<SuccessResponse<FollowResponse>> followArtists(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                                         @PathVariable("id") String artistId) {
        return SuccessResponse.of(followService.followArtist(jwtTokenInfo.getUserId(), artistId)).asHttp(HttpStatus.OK);
    }

    @GetMapping("/follow/artists")
    public ResponseEntity<SuccessResponse<SliceResponse<ArtistListResponse>>> getFollowArtists(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                                                               @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                                                                               @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                                               @RequestParam(name = "direction", required = false, defaultValue = "DESC") Direction direction) {
        return SuccessResponse.of(followService.getFollowArtists(jwtTokenInfo.getUserId(), size, page, direction)).asHttp(HttpStatus.OK);
    }

    @DeleteMapping("/follow/artists")
    public ResponseEntity<Void> deleteFollowArtists(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                    @RequestBody DeleteFollowRequest deleteFollowRequest) {
        followService.deleteFollowArtists(jwtTokenInfo.getUserId(), deleteFollowRequest.getIds());
        return ResponseEntity.noContent().build();
    }
}
