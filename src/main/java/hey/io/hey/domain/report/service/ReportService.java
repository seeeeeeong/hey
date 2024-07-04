package hey.io.hey.domain.report.service;

import hey.io.hey.common.exception.BusinessException;
import hey.io.hey.common.exception.ErrorCode;
import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.performance.repository.PerformanceRepository;
import hey.io.hey.domain.report.domain.PerformanceReport;
import hey.io.hey.domain.report.dto.ReportRequest;
import hey.io.hey.domain.report.repository.PerformanceReportRepository;
import hey.io.hey.domain.user.domain.User;
import hey.io.hey.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final PerformanceRepository performanceRepository;
    private final UserRepository userRepository;
    private final PerformanceReportRepository performanceReportRepository;

    @Transactional
    public void reportPerformance(String performanceId, Long userId, ReportRequest request) {
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PERFORMANCE_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        PerformanceReport performanceReport = PerformanceReport.of(request, user, performance);
        performanceReportRepository.save(performanceReport);

    }

}
