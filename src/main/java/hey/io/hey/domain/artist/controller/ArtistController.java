package hey.io.hey.domain.artist.controller;

import hey.io.hey.common.response.SuccessResponse;
import hey.io.hey.domain.artist.dto.ArtistResponse;
import hey.io.hey.domain.artist.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<ArtistResponse>> getArtist(@PathVariable("id") String artistId) {
        return SuccessResponse.of(artistService.getArtist(artistId)).asHttp(HttpStatus.OK);
    }

}
