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

import java.time.LocalDate;
import java.util.List;

import static hey.io.hey.domain.performance.domain.QPerformance.performance;
import static hey.io.hey.domain.performance.domain.QPerformanceArtist.performanceArtist;

@RequiredArgsConstructor
public class PerformanceArtistQueryRepositoryImpl implements PerformanceArtistQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ArtistListResponse> getPerformanceArtists(String performanceId) {
        return queryFactory.select(
                new QArtistListResponse(performanceArtist.artist.id, performanceArtist.artist.artistName, performanceArtist.artist.artistImage))
                .from(performanceArtist)
                .where(performanceArtist.performance.id.eq(performanceId))
                .orderBy(performanceArtist.artist.artistName.desc())
                .fetch();

    }

    @Override
    public Slice<PerformanceResponse> getArtistPerformances(String artistId, Pageable pageable, Sort.Direction direction) {

        int pageSize = pageable.getPageSize();

        List<PerformanceResponse> content = queryFactory.select(
                new QPerformanceResponse(performanceArtist.performance.id, performanceArtist.performance.title, performanceArtist.performance.startDate, performanceArtist.performance.endDate, performanceArtist.performance.poster,
                                         performanceArtist.performance.theater, performanceArtist.performance.status, performanceArtist.performance.createdAt)).distinct()
                .from(performanceArtist)
                .where(performanceArtist.artist.id.eq(artistId))
                .orderBy(direction.isAscending() ? performanceArtist.performance.startDate.asc() : performanceArtist.performance.startDate.desc())
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

    @Override
    public List<ArtistListResponse> getArtistsByPerformanceStartDate() {
        return queryFactory.select(
                new QArtistListResponse(performanceArtist.artist.id, performanceArtist.artist.artistName, performanceArtist.artist.artistImage))
                .from(performanceArtist)
                .where(performanceArtist.performance.startDate.eq(LocalDate.now().plusDays(1)))
                .fetch();
    }
}
