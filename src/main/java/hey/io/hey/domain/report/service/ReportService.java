package hey.io.hey.domain.report.service;

import hey.io.hey.common.exception.BusinessException;
import hey.io.hey.common.exception.ErrorCode;
import hey.io.hey.domain.artist.domain.ArtistEntity;
import hey.io.hey.domain.artist.repository.ArtistRepository;
import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.performance.repository.PerformanceRepository;
import hey.io.hey.domain.report.domain.ArtistReport;
import hey.io.hey.domain.report.domain.PerformanceReport;
import hey.io.hey.domain.report.dto.ReportRequest;
import hey.io.hey.domain.report.dto.ReportResponse;
import hey.io.hey.domain.report.repository.ArtistReportRepository;
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
    private final ArtistRepository artistRepository;
    private final ArtistReportRepository artistReportRepository;


    @Transactional
    public ReportResponse reportPerformance(String performanceId, Long userId, ReportRequest request) {
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PERFORMANCE_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        PerformanceReport performanceReport = PerformanceReport.of(request, user, performance);
        performanceReportRepository.save(performanceReport);

        return new ReportResponse(performanceId, userId);
    }

    @Transactional
    public ReportResponse reportArtist(String artistId, Long userId, ReportRequest request) {
        ArtistEntity artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ARTIST_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        ArtistReport artistReport = ArtistReport.of(request, user, artist);
        artistReportRepository.save(artistReport);

        return new ReportResponse(artistId, userId);

    }

}
