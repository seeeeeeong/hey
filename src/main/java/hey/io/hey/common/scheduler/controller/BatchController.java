package hey.io.hey.common.scheduler.controller;

import com.google.api.Http;
import com.google.firebase.messaging.FirebaseMessagingException;
import hey.io.hey.common.response.SuccessResponse;
import hey.io.hey.common.scheduler.dto.PerformanceBatchUpdateRequest;
import hey.io.hey.domain.performance.service.PerformanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/batch")
public class BatchController {

    private final PerformanceService performanceService;

    @GetMapping("/performances")
    public ResponseEntity<SuccessResponse<Integer>> updatePerformancesBatch(@Valid @RequestBody PerformanceBatchUpdateRequest request) {
        int count = performanceService.updatePerformancesBatch(request.getFrom(), request.getTo(), request.getRows());
        return SuccessResponse.of(count).asHttp(HttpStatus.OK);
    }

    @GetMapping("/performances/status")
    public ResponseEntity<SuccessResponse<Integer>> updatePerformanceStatusBatch() {
        int count = performanceService.updatePerformanceStatusBatch();
        return SuccessResponse.of(count).asHttp(HttpStatus.OK);
    }

    @GetMapping("/performances/rank")
    public ResponseEntity<SuccessResponse<Integer>> updateBoxOfficeRankBatch() {
        int count = performanceService.updateBoxOfficeRankBatch();
        return SuccessResponse.of(count).asHttp(HttpStatus.OK);
    }

    @GetMapping("/performances/notification")
    public ResponseEntity<SuccessResponse<Integer>> sendPerformancesNotification() throws FirebaseMessagingException {
        int count = performanceService.sendPerformancesNotification();
        return SuccessResponse.of(count).asHttp(HttpStatus.OK);
    }
}
