package hey.io.hey.domain.performance.repository;

import hey.io.hey.domain.artist.domain.ArtistEntity;
import hey.io.hey.domain.artist.dto.ArtistListResponse;
import hey.io.hey.domain.performance.dto.PerformanceResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PerformanceArtistQueryRepository {

    List<ArtistListResponse> getPerformanceArtists(String performanceId);
    Slice<PerformanceResponse> getArtistPerformances(String artistId, Pageable pageable, Sort.Direction direction);

    List<ArtistEntity> getArtistsByPerformanceStartDate();
}
