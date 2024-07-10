package hey.io.hey.domain.album.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hey.io.hey.domain.album.domain.AlbumEntity;
import hey.io.hey.domain.album.domain.QAlbumEntity;
import hey.io.hey.domain.album.dto.AlbumResponse;
import hey.io.hey.domain.album.dto.QAlbumResponse;
import hey.io.hey.domain.artist.domain.ArtistEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import java.util.List;

import static hey.io.hey.domain.album.domain.QAlbumEntity.albumEntity;
import static hey.io.hey.domain.performance.domain.QPerformance.performance;

@RequiredArgsConstructor
public class AlbumQueryRepositoryImpl implements AlbumQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findAllIds() {
        return queryFactory.select(albumEntity.id)
                .from(albumEntity)
                .fetch();
    }

    @Override
    public Slice<AlbumResponse> getArtistAlbums(ArtistEntity artist, Pageable pageable, Sort.Direction direction) {

        int pageSize = pageable.getPageSize();

        List<AlbumResponse> content = queryFactory.select(
                new QAlbumResponse(albumEntity))
                .from(albumEntity)
                .where(albumEntity.artist.eq(artist))
                .orderBy(direction.isAscending() ? albumEntity.createdAt.asc() : albumEntity.createdAt.desc())
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
