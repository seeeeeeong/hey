package hey.io.hey.domain.follow.repository;

import hey.io.hey.domain.artist.dto.ArtistListResponse;
import hey.io.hey.domain.performance.dto.PerformanceResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

public interface FollowArtistQueryRepository {

    Slice<ArtistListResponse> getFollowArtists(Long userId, Pageable pageable, Sort.Direction direction);


}
