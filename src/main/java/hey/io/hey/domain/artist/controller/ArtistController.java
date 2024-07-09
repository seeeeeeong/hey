package hey.io.hey.domain.artist.controller;

import hey.io.hey.domain.artist.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

}
