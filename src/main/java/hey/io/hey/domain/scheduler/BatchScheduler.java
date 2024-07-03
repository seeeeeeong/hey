package hey.io.hey.domain.scheduler;

import com.google.firebase.messaging.FirebaseMessagingException;
import hey.io.hey.domain.performance.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final PerformanceService performanceService;

    @Value("${kopis.performance.batch-count}")
    private int performanceBatchCount;

    @Scheduled(cron = "0 0 0/3 * * *")
    public void updatePerformances() {
        performanceService.updatePerformancesBatch(LocalDate.now().minusMonths(1), LocalDate.now().plusMonths(5), performanceBatchCount);
    }

    @Scheduled(cron = "10 0 0 * * *")
    public void updatePerformanceState() {
        performanceService.updatePerformanceStatusBatch();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void sendPerformancesNotification() throws FirebaseMessagingException {
        performanceService.sendPerformancesNotification();
    }
}
