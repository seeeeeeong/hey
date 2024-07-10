package hey.io.hey.domain.follow.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hey.io.hey.domain.follow.domain.QPerformanceFollow;
import hey.io.hey.domain.performance.dto.PerformanceResponse;
import hey.io.hey.domain.performance.dto.QPerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import java.util.List;

import static hey.io.hey.domain.follow.domain.QPerformanceFollow.performanceFollow;


@RequiredArgsConstructor
public class FollowPerformanceQueryRepositoryImpl implements FollowPerformanceQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<PerformanceResponse> getFollowPerformances(Long userId, Pageable pageable, Sort.Direction direction) {
        int pageSize = pageable.getPageSize();
        List<PerformanceResponse> content = queryFactory.select(
                new QPerformanceResponse(performanceFollow.performance.id, performanceFollow.performance.title, performanceFollow.performance.startDate, performanceFollow.performance.endDate,
                        performanceFollow.performance.poster, performanceFollow.performance.theater, performanceFollow.performance.status, performanceFollow.performance.createdAt))
                .from(performanceFollow)
                .where(performanceFollow.user.userId.eq(userId))
                .orderBy(direction.isAscending() ? performanceFollow.createdAt.asc() : performanceFollow.createdAt.desc())
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
