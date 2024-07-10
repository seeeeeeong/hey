package hey.io.hey.domain.follow.repository;

import hey.io.hey.domain.follow.domain.PerformanceFollow;
import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowPerformanceRepository extends JpaRepository<PerformanceFollow, Long> , FollowPerformanceQueryRepository {

    Optional<PerformanceFollow> findByUserAndPerformance(User user, Performance performance);

}
