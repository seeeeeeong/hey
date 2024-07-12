package hey.io.hey.scheduler.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import hey.io.hey.common.response.SuccessResponse;
import hey.io.hey.domain.album.service.AlbumService;
import hey.io.hey.domain.artist.service.ArtistService;
import hey.io.hey.domain.performance.service.PerformanceService;
import hey.io.hey.scheduler.dto.PerformanceBatchUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Update Performance List", description = "Update Performance List API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200")
    })
    @GetMapping("/performances")
    public ResponseEntity<SuccessResponse<Integer>> updatePerformancesBatch(@Valid @RequestBody PerformanceBatchUpdateRequest request) {
        return SuccessResponse.of(performanceService.updatePerformancesBatch(request.getFrom(), request.getTo(), request.getRows())).asHttp(HttpStatus.OK);
    }

    @Operation(summary = "Update Performance Status", description = "Update Performance Status API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200")
    })
    @GetMapping("/performances/status")
    public ResponseEntity<SuccessResponse<Integer>> updatePerformanceStatusBatch() {
        return SuccessResponse.of(performanceService.updatePerformanceStatusBatch()).asHttp(HttpStatus.OK);
    }

    @Operation(summary = "Update Performance Rank", description = "Update Performance Rank API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200")
    })
    @GetMapping("/performances/rank")
    public ResponseEntity<SuccessResponse<Integer>> updateBoxOfficeRankBatch() {
        return SuccessResponse.of(performanceService.updateBoxOfficeRankBatch()).asHttp(HttpStatus.OK);
    }

    @Operation(summary = "Send Performance Notification", description = "Send Performance Notification API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200")
    })
    @GetMapping("/performances/notification")
    public ResponseEntity<SuccessResponse<Integer>> sendPerformancesNotification() throws FirebaseMessagingException {
        return SuccessResponse.of(performanceService.sendPerformancesNotification()).asHttp(HttpStatus.OK);
    }

    @Operation(summary = "Send Artist`s Performance Notification", description = "Send Artist`s Performance Notification API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200")
    })
    @GetMapping("/artists/notification")
    public ResponseEntity<SuccessResponse<Integer>> sendArtistsNotification() throws FirebaseMessagingException {
        return SuccessResponse.of(artistService.sendArtistsNotification()).asHttp(HttpStatus.OK);
    }

    @Operation(summary = "Update Artists", description = "Update Artists API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200")
    })
    @GetMapping("/artists")
    public ResponseEntity<SuccessResponse<Integer>> updateArtistsBatch() throws IOException, ParseException, SpotifyWebApiException, ExecutionException, InterruptedException {
        return SuccessResponse.of(artistService.updateArtistsBatch()).asHttp(HttpStatus.OK);
    }

    @Operation(summary = "Update Albums", description = "Update Albums API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200")
    })
    @GetMapping("/artists/album")
    public ResponseEntity<SuccessResponse<Integer>> updateAlbumsBatch() throws IOException, ParseException, SpotifyWebApiException {
        return SuccessResponse.of(albumService.updateAlbumsBatch()).asHttp(HttpStatus.OK);
    }
}
