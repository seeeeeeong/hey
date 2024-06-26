package hey.io.hey.domain.performance.repository;

import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.performance.dto.PerformanceFilterRequest;
import hey.io.hey.domain.performance.dto.PerformanceResponse;
import hey.io.hey.domain.performance.dto.PerformanceSearchRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort.Direction;

public interface PerformanceQueryRepository {

    Slice<PerformanceResponse> getPerformancesByCondition(PerformanceFilterRequest request, Pageable pageable, Direction direction);
    Slice<PerformanceResponse> searchPerformances(PerformanceSearchRequest request, Pageable pageable, Direction direction);

}
