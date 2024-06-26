package hey.io.hey.domain.performance.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import hey.io.hey.domain.performance.domain.enums.PerformanceStatus;
import hey.io.hey.domain.performance.dto.PerformanceFilterRequest;
import hey.io.hey.domain.performance.dto.PerformanceResponse;
import hey.io.hey.domain.performance.dto.PerformanceSearchRequest;
import hey.io.hey.domain.performance.dto.QPerformanceResponse;
import lombok.RequiredArgsConstructor;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static hey.io.hey.domain.performance.domain.QPerformance.performance;
import static hey.io.hey.domain.performance.domain.QPerformancePrice.performancePrice;


@RequiredArgsConstructor
public class PerformanceQueryRepositoryImpl implements PerformanceQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findAllIds() {
        return queryFactory.select(performance.id)
                .from(performance)
                .fetch();
    }

    @Override
    public Slice<PerformanceResponse> getPerformancesByCondition(PerformanceFilterRequest request, Pageable pageable, Sort.Direction direction) {

        int pageSize = pageable.getPageSize();
        List<PerformanceResponse> content = queryFactory.select(
                new QPerformanceResponse(performance.id, performance.title, performance.startDate, performance.endDate, performance.poster, performance.theater))
                .from(performance)
                .where(inStatus(request.getStatuses()))
                .leftJoin(performancePrice)
                .on(performancePrice.performance.eq(performance))
                .orderBy(direction.isAscending() ? performance.createdAt.asc() : performance.createdAt.desc())
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
    public Slice<PerformanceResponse> searchPerformances(PerformanceSearchRequest request, Pageable pageable, Sort.Direction direction) {
        int pageSize = pageable.getPageSize();
        List<PerformanceResponse> content = queryFactory.select(
                        new QPerformanceResponse(performance.id, performance.title, performance.startDate, performance.endDate, performance.poster, performance.theater))
                .from(performance)
                .where(performance.title.contains(request.getKeyword()))
                .leftJoin(performancePrice)
                .on(performancePrice.performance.eq(performance))
                .orderBy(direction.isAscending() ? performance.createdAt.asc() : performance.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageSize + 1)
                .fetch();

        boolean hasNext = false;
        if (content.size() > pageSize) {
            content.remove(pageSize);
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);    }

    private BooleanExpression inStatus(List<PerformanceStatus> statuses) {
        return ObjectUtils.isEmpty(statuses) ? null : performance.status.in(statuses);
    }

}
