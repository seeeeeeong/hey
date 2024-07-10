package hey.io.hey.domain.artist.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hey.io.hey.domain.artist.dto.ArtistListResponse;
import hey.io.hey.domain.artist.dto.QArtistListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import java.util.List;

import static hey.io.hey.domain.artist.domain.QArtistEntity.artistEntity;
import static hey.io.hey.domain.performance.domain.QPerformance.performance;

@RequiredArgsConstructor
public class ArtistQueryRepositoryImpl implements ArtistQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findAllIds() {
        return queryFactory.select(artistEntity.id)
                .from(artistEntity)
                .fetch();
    }

    @Override
    public Slice<ArtistListResponse> searchArtists(String keyword, Pageable pageable, Sort.Direction direction) {
        int pageSize = pageable.getPageSize();

        List<ArtistListResponse> content = queryFactory.select(
                new QArtistListResponse(artistEntity.id, artistEntity.artistName, artistEntity.artistImage))
                .from(artistEntity)
                .where(artistEntity.artistName.contains(keyword))
                .orderBy(direction.isAscending() ? artistEntity.createdAt.asc() : artistEntity.createdAt.desc())
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
