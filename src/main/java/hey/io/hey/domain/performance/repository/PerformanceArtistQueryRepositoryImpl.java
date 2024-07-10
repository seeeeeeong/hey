package hey.io.hey.domain.performance.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hey.io.hey.domain.artist.domain.ArtistEntity;
import hey.io.hey.domain.artist.dto.ArtistListResponse;
import hey.io.hey.domain.artist.dto.QArtistListResponse;
import hey.io.hey.domain.performance.domain.QPerformanceArtist;
import hey.io.hey.domain.performance.dto.PerformanceResponse;
import hey.io.hey.domain.performance.dto.QPerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import java.util.List;

import static hey.io.hey.domain.performance.domain.QPerformance.performance;

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

    @Override
    public Slice<PerformanceResponse> getArtistPerformances(String artistId, Pageable pageable, Sort.Direction direction) {

        int pageSize = pageable.getPageSize();

        List<PerformanceResponse> content = queryFactory.select(
                new QPerformanceResponse(QPerformanceArtist.performanceArtist.performance.id, QPerformanceArtist.performanceArtist.performance.title, QPerformanceArtist.performanceArtist.performance.startDate, QPerformanceArtist.performanceArtist.performance.endDate, QPerformanceArtist.performanceArtist.performance.poster,
                                         QPerformanceArtist.performanceArtist.performance.theater, QPerformanceArtist.performanceArtist.performance.status, QPerformanceArtist.performanceArtist.performance.createdAt)).distinct()
                .from(QPerformanceArtist.performanceArtist)
                .where(QPerformanceArtist.performanceArtist.artist.id.eq(artistId))
                .orderBy(direction.isAscending() ? QPerformanceArtist.performanceArtist.performance.startDate.asc() : QPerformanceArtist.performanceArtist.performance.startDate.desc())
                .offset(pageable.getOffset())
                .limit(pageSize + 1)
                .fetch();


        boolean hasNext = false;
        if (content.size() > pageSize) {
            content.remove(pageSize);
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }
}
