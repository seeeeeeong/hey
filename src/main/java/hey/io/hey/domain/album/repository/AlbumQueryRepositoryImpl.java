package hey.io.hey.domain.album.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hey.io.hey.domain.album.domain.AlbumEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static hey.io.hey.domain.album.domain.QAlbumEntity.albumEntity;

@RequiredArgsConstructor
public class AlbumQueryRepositoryImpl implements AlbumQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findAllIds() {
        return queryFactory.select(albumEntity.id)
                .from(albumEntity)
                .fetch();
    }
}
