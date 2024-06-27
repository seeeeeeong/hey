package hey.io.hey.domain.performance.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hey.io.hey.domain.performance.domain.BoxOfficeRank;
import hey.io.hey.domain.boxoffice.domain.QBoxOfficeRank;
import hey.io.hey.domain.performance.domain.enums.TimePeriod;
import hey.io.hey.domain.performance.dto.BoxOfficeRankRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

import static hey.io.hey.domain.boxoffice.domain.QBoxOfficeRank.boxOfficeRank;

@RequiredArgsConstructor
public class BoxOfficeRankQueryRepositoryImpl implements BoxOfficeRankQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<BoxOfficeRank> findBoxOfficeRank(BoxOfficeRankRequest request) {
        BoxOfficeRank boxOfficeRank = queryFactory.selectFrom(QBoxOfficeRank.boxOfficeRank)
                .where(eqTimePeriod(request.getTimePeriod()))
                .orderBy(QBoxOfficeRank.boxOfficeRank.createdAt.desc())
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(boxOfficeRank);
    }

    private BooleanExpression eqTimePeriod(TimePeriod timePeriod) {
        if (ObjectUtils.isEmpty(timePeriod)) {
            return null;
        }
        return boxOfficeRank.timePeriod.eq(timePeriod);
    }
}
