package hey.io.hey.domain.artist.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static hey.io.hey.domain.artist.domain.QArtistEntity.artistEntity;

@RequiredArgsConstructor
public class ArtistQueryRepositoryImpl implements ArtistQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findAllIds() {
        return queryFactory.select(artistEntity.id)
                .from(artistEntity)
                .fetch();
    }
}
