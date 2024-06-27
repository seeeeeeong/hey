package hey.io.hey.domain.follow.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hey.io.hey.domain.performance.dto.PerformanceResponse;
import hey.io.hey.domain.performance.dto.QPerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import java.util.List;

import static hey.io.hey.domain.follow.domain.QFollow.follow;
import static hey.io.hey.domain.performance.domain.QPerformance.performance;

@RequiredArgsConstructor
public class FollowQueryRepositoryImpl implements FollowQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<PerformanceResponse> getFollow(Long userId, Pageable pageable, Sort.Direction direction) {
        int pageSize = pageable.getPageSize();
        List<PerformanceResponse> content = queryFactory.select(
                new QPerformanceResponse(follow.performance.id, follow.performance.title, follow.performance.startDate, follow.performance.endDate,
                                        follow.performance.poster, follow.performance.theater))
                .from(follow)
                .where(follow.user.userId.eq(userId))
                .orderBy(direction.isAscending() ? follow.createdAt.asc() : follow.createdAt.desc())
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
