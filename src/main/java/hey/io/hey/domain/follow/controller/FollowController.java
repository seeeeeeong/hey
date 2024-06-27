package hey.io.hey.domain.follow.controller;


import hey.io.hey.common.resolver.AuthUser;
import hey.io.hey.common.response.SliceResponse;
import hey.io.hey.common.response.SuccessResponse;
import hey.io.hey.common.security.jwt.JwtTokenInfo;
import hey.io.hey.domain.follow.dto.FollowResponse;
import hey.io.hey.domain.follow.service.FollowService;
import hey.io.hey.domain.performance.dto.PerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow/{id}")
    public ResponseEntity<SuccessResponse<FollowResponse>> follow(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                                  @PathVariable("id") String performanceId) {
        return SuccessResponse.of(followService.follow(jwtTokenInfo.getUserId(), performanceId)).asHttp(HttpStatus.OK);
    }

    @GetMapping("/follow")
    public ResponseEntity<SuccessResponse<SliceResponse<PerformanceResponse>>> getFollow(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                                                         @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                                                                         @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                                         @RequestParam(name = "direction", required = false, defaultValue = "DESC") Direction direction) {
        return SuccessResponse.of(followService.getFollow(jwtTokenInfo.getUserId(), size, page, direction)).asHttp(HttpStatus.OK);
    }

    @DeleteMapping("/follow/{id}")
    public ResponseEntity<SuccessResponse<FollowResponse>> deleteFollow(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                                        @PathVariable("id") String performanceId) {
        return SuccessResponse.of(followService.deleteFollow(jwtTokenInfo.getUserId(), performanceId)).asHttp(HttpStatus.OK);
    }

}
