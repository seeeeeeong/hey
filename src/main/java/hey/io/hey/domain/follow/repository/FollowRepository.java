package hey.io.hey.domain.follow.repository;

import hey.io.hey.domain.follow.domain.Follow;
import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> , FollowQueryRepository{

    Optional<Follow> findByUserAndPerformance(User user, Performance performance);

}
