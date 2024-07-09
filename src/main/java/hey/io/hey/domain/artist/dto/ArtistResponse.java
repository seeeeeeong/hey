package hey.io.hey.domain.artist.dto;

import hey.io.hey.domain.artist.domain.ArtistEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtistResponse {

    private String id;
    private String artistName;
    private String artistImage;
    private List<String> genre = new ArrayList<>();

    public ArtistResponse(ArtistEntity artist) {
        this.id = artist.getId();
        this.artistName = artist.getArtistName();
        this.artistImage = artist.getArtistImage();
        this.genre = artist.getGenre();
    }

}
