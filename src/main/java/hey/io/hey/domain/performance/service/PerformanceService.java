package hey.io.hey.domain.performance.service;

import hey.io.hey.common.response.SliceResponse;
import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.performance.dto.CreatePerformanceRequest;
import hey.io.hey.domain.performance.dto.PerformanceFilterRequest;
import hey.io.hey.domain.performance.dto.PerformanceResponse;
import hey.io.hey.domain.performance.dto.PerformanceSearchRequest;
import hey.io.hey.domain.performance.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformanceService {

    private final PerformanceRepository performanceRepository;

    @Transactional
    public void createPerformance(CreatePerformanceRequest request) {

        Performance performance = Performance.builder()
                .id(request.getId())
                .place(request.getPlace())
                .title(request.getTitle())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .theater(request.getTheater())
                .cast(request.getCast())
                .runtime(request.getRuntime())
                .age(request.getAge())
                .price(request.getPrice())
                .poster(request.getPoster())
                .status(request.getStatus())
                .area(request.getArea())
                .storyUrls(request.getStoryUrls())
                .schedule(request.getSchedule())
                .build();

        performanceRepository.save(performance);
    }

    public SliceResponse<PerformanceResponse> getPerformancesByCondition(PerformanceFilterRequest request, int size, int page, Direction direction) {
        Slice<PerformanceResponse> performances = performanceRepository.getPerformancesByCondition(request, Pageable.ofSize(size).withPage(page), direction);
        return new SliceResponse<>(performances);
    }

    public SliceResponse<PerformanceResponse> searchPerformances(PerformanceSearchRequest request, int size, int page, Direction direction) {
        Slice<PerformanceResponse> performances = performanceRepository.searchPerformances(request, Pageable.ofSize(size).withPage(page), direction);
        return new SliceResponse<>(performances);
    }

    public List<PerformanceResponse> getNewPerformances() {
        List<Performance> newPerformances = performanceRepository.findTop5ByOrderByCreatedAtDesc();

        return newPerformances.stream()
                .map(PerformanceResponse::new)
                .collect(Collectors.toList());
    }

}
