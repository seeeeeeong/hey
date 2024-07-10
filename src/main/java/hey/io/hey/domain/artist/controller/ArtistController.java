package hey.io.hey.domain.artist.controller;

import hey.io.hey.common.response.SliceResponse;
import hey.io.hey.common.response.SuccessResponse;
import hey.io.hey.domain.album.dto.AlbumResponse;
import hey.io.hey.domain.artist.dto.ArtistListResponse;
import hey.io.hey.domain.artist.dto.ArtistResponse;
import hey.io.hey.domain.artist.service.ArtistService;
import hey.io.hey.domain.performance.dto.PerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<ArtistResponse>> getArtist(@PathVariable("id") String artistId) {
        return SuccessResponse.of(artistService.getArtist(artistId)).asHttp(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<SuccessResponse<SliceResponse<ArtistListResponse>>> searchArtists(@RequestParam(value = "keyword", required = false) String keyword,
                                                                                            @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                                                                            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                                            @RequestParam(name = "direction", required = false, defaultValue = "DESC") Sort.Direction direction) {
        return SuccessResponse.of(artistService.searchArtists(keyword, size, page, direction)).asHttp(HttpStatus.OK);
    }

    @GetMapping("/{id}/albums")
    public ResponseEntity<SuccessResponse<SliceResponse<AlbumResponse>>> getAlbums(@PathVariable("id") String artistId,
                                                                                   @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                                                                   @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                                   @RequestParam(name = "direction", required = false, defaultValue = "DESC") Sort.Direction direction) {
        return SuccessResponse.of(artistService.getAlbums(artistId, size, page, direction)).asHttp(HttpStatus.OK);
    }

    @GetMapping("/{id}/performances")
    public ResponseEntity<SuccessResponse<SliceResponse<PerformanceResponse>>> getArtistPerformances(@PathVariable("id") String artistId,
                                                                                                     @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                                                                                     @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                                                     @RequestParam(name = "direction", required = false, defaultValue = "DESC") Sort.Direction direction) {
        return SuccessResponse.of(artistService.getArtistPerformances(artistId, size, page, direction)).asHttp(HttpStatus.OK);
    }

    @GetMapping("/rank")
    public ResponseEntity<SuccessResponse<List<ArtistListResponse>>> getArtistRank() {
        return SuccessResponse.of(artistService.getArtistRank()).asHttp(HttpStatus.OK);
    }
}
