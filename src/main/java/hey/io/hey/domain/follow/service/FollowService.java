package hey.io.hey.domain.follow.service;

import hey.io.hey.common.exception.BusinessException;
import hey.io.hey.common.exception.ErrorCode;
import hey.io.hey.common.response.SliceResponse;
import hey.io.hey.domain.follow.domain.Follow;
import hey.io.hey.domain.follow.dto.FollowResponse;
import hey.io.hey.domain.follow.repository.FollowRepository;
import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.performance.dto.PerformanceResponse;
import hey.io.hey.domain.performance.repository.PerformanceRepository;
import hey.io.hey.domain.user.domain.User;
import hey.io.hey.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final UserRepository userRepository;
    private final PerformanceRepository performanceRepository;
    private final FollowRepository followRepository;

    @Transactional
    public FollowResponse follow(Long userId, String performanceId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PERFORMANCE_NOT_FOUND));

        followRepository.findByUserAndPerformance(user, performance)
                .ifPresent(it -> {
                    throw new BusinessException(ErrorCode.ALREADY_FOLLOWED_PERFORMANCE);
                });
        followRepository.save(Follow.of(user, performance));
        return new FollowResponse(performance.getId(), "Follow Success");
    }

    public SliceResponse<PerformanceResponse> getFollow(Long userId, int size, int page, Direction direction) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Slice<PerformanceResponse> followedPerformance = followRepository.getFollow(user.getUserId(), Pageable.ofSize(size).withPage(page), direction);
        return new SliceResponse<>(followedPerformance);
    }

}
