package hey.io.hey.domain.artist.dto;

import hey.io.hey.domain.artist.domain.ArtistEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "아티스트 상세 Response")
public class ArtistResponse {

    @Schema(description = "아티스트 ID")
    private String id;

    @Schema(description = "아티스트명")
    private String artistName;

    @Schema(description = "아티스트 이미지")
    private String artistImage;

    @Schema(description = "장르")
    private List<String> genre = new ArrayList<>();

    public ArtistResponse(ArtistEntity artist) {
        this.id = artist.getId();
        this.artistName = artist.getArtistName();
        this.artistImage = artist.getArtistImage();
        this.genre = artist.getGenre();
    }

}
