package hey.io.hey.domain.performance.service;

import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.performance.dto.CreatePerformanceRequest;
import hey.io.hey.domain.performance.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .performanceStatus(request.getPerformanceStatus())
                .area(request.getArea())
                .storyUrls(request.getStoryUrls())
                .schedule(request.getSchedule())
                .build();

        performanceRepository.save(performance);
    }

}
