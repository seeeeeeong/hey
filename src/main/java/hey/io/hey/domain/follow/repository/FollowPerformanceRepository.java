package hey.io.hey.domain.follow.repository;

import hey.io.hey.domain.follow.domain.PerformanceFollow;
import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowPerformanceRepository extends JpaRepository<PerformanceFollow, Long> , FollowPerformanceQueryRepository {

    Optional<PerformanceFollow> findByUserAndPerformance(User user, Performance performance);

    @Modifying
    @Query("delete from PerformanceFollow p where p.performance.id in :ids")
    void deleteAllByIds(@Param("ids") List<String> performanceIds);

}
