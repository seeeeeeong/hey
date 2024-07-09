package hey.io.hey.domain.performance.repository;

import hey.io.hey.domain.artist.domain.ArtistEntity;
import hey.io.hey.domain.artist.dto.ArtistListResponse;

import java.util.List;

public interface PerformanceArtistQueryRepository {

    List<ArtistListResponse> getPerformanceArtists(String performanceId);
}
