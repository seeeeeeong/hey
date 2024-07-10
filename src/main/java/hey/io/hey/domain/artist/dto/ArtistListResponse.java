package hey.io.hey.domain.artist.dto;

import com.querydsl.core.annotations.QueryProjection;
import hey.io.hey.domain.artist.domain.ArtistEntity;
import hey.io.hey.domain.performance.domain.PerformanceArtist;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArtistListResponse {

    private String id;
    private String artistName;
    private String artistImage;

    @QueryProjection
    public ArtistListResponse(String id, String artistName, String artistImage) {
        this.id = id;
        this.artistName = artistName;
        this.artistImage = artistImage;
    }
}
