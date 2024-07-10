package hey.io.hey.domain.follow.repository;

import hey.io.hey.domain.artist.domain.ArtistEntity;
import hey.io.hey.domain.follow.domain.ArtistFollow;
import hey.io.hey.domain.follow.domain.PerformanceFollow;
import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowArtistRepository extends JpaRepository<ArtistFollow, Long>, FollowArtistQueryRepository {

    Optional<ArtistFollow> findByUserAndArtist(User user, ArtistEntity artist);

}
