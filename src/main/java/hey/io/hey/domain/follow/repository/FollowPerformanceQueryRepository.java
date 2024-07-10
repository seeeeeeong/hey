package hey.io.hey.domain.follow.repository;

import hey.io.hey.domain.performance.dto.PerformanceResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort.Direction;

public interface FollowPerformanceQueryRepository {

    Slice<PerformanceResponse> getFollowPerformances(Long userId, Pageable pageable, Direction direction);

}
