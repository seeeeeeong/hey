package hey.io.hey.domain.performance.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PerformanceArtistQueryRepositoryImpl implements PerformanceArtistQueryRepository{

    private final JPAQueryFactory queryFactory;

}
