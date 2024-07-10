package hey.io.hey.domain.follow.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hey.io.hey.domain.artist.dto.ArtistListResponse;
import hey.io.hey.domain.artist.dto.QArtistListResponse;
import hey.io.hey.domain.follow.domain.QArtistFollow;
import hey.io.hey.domain.performance.dto.QPerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import java.util.List;

import static hey.io.hey.domain.follow.domain.QArtistFollow.artistFollow;

@RequiredArgsConstructor
public class FollowArtistQueryRepositoryImpl implements FollowArtistQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<ArtistListResponse> getFollowArtists(Long userId, Pageable pageable, Sort.Direction direction) {
        int pageSize = pageable.getPageSize();

        List<ArtistListResponse> content = queryFactory.select(
                        new QArtistListResponse(artistFollow.artist.id, artistFollow.artist.artistName, artistFollow.artist.artistImage))
                .from(artistFollow)
                .where(artistFollow.user.userId.eq(userId))
                .orderBy(direction.isAscending() ? artistFollow.createdAt.asc() : artistFollow.createdAt.desc())
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
