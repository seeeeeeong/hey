package hey.io.hey.scheduler.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import hey.io.hey.common.response.SuccessResponse;
import hey.io.hey.domain.album.service.AlbumService;
import hey.io.hey.domain.artist.service.ArtistService;
import hey.io.hey.domain.performance.service.PerformanceService;
import hey.io.hey.scheduler.dto.PerformanceBatchUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/batch")
public class BatchController {

    private final PerformanceService performanceService;
    private final ArtistService artistService;
    private final AlbumService albumService;

    @GetMapping("/performances")
    public ResponseEntity<SuccessResponse<Integer>> updatePerformancesBatch(@Valid @RequestBody PerformanceBatchUpdateRequest request) {
        return SuccessResponse.of(performanceService.updatePerformancesBatch(request.getFrom(), request.getTo(), request.getRows())).asHttp(HttpStatus.OK);
    }

    @GetMapping("/performances/status")
    public ResponseEntity<SuccessResponse<Integer>> updatePerformanceStatusBatch() {
        return SuccessResponse.of(performanceService.updatePerformanceStatusBatch()).asHttp(HttpStatus.OK);
    }

    @GetMapping("/performances/rank")
    public ResponseEntity<SuccessResponse<Integer>> updateBoxOfficeRankBatch() {
        return SuccessResponse.of(performanceService.updateBoxOfficeRankBatch()).asHttp(HttpStatus.OK);
    }

    @GetMapping("/performances/notification")
    public ResponseEntity<SuccessResponse<Integer>> sendPerformancesNotification() throws FirebaseMessagingException {
        return SuccessResponse.of(performanceService.sendPerformancesNotification()).asHttp(HttpStatus.OK);
    }

    @GetMapping("/artists")
    public ResponseEntity<SuccessResponse<Integer>> updateArtistsBatch() throws IOException, ParseException, SpotifyWebApiException, ExecutionException, InterruptedException {
        return SuccessResponse.of(artistService.updateArtistsBatch()).asHttp(HttpStatus.OK);
    }

    @GetMapping("/artists/album")
    public ResponseEntity<SuccessResponse<Integer>> updateAlbumsBatch() throws IOException, ParseException, SpotifyWebApiException {
        return SuccessResponse.of(albumService.updateAlbumsBatch()).asHttp(HttpStatus.OK);
    }
}
