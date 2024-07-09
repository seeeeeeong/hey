package hey.io.hey.domain.performance.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hey.io.hey.domain.artist.domain.ArtistEntity;
import hey.io.hey.domain.artist.dto.ArtistListResponse;
import hey.io.hey.domain.artist.dto.QArtistListResponse;
import hey.io.hey.domain.performance.domain.QPerformanceArtist;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PerformanceArtistQueryRepositoryImpl implements PerformanceArtistQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ArtistListResponse> getPerformanceArtists(String performanceId) {
        return queryFactory.select(
                new QArtistListResponse(QPerformanceArtist.performanceArtist))
                .from(QPerformanceArtist.performanceArtist)
                .where(QPerformanceArtist.performanceArtist.performance.id.eq(performanceId))
                .orderBy(QPerformanceArtist.performanceArtist.artist.artistName.desc())
                .fetch();

    }
}
