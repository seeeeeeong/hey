package hey.io.hey.domain.artist.dto;

import com.querydsl.core.annotations.QueryProjection;
import hey.io.hey.domain.artist.domain.ArtistEntity;
import hey.io.hey.domain.performance.domain.PerformanceArtist;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "아티스트 리스트 Response")
public class ArtistListResponse {

    @Schema(description = "아티스트 ID")
    private String id;

    @Schema(description = "아티스트 명")
    private String artistName;

    @Schema(description = "아티스트 이미지")
    private String artistImage;

    @QueryProjection
    public ArtistListResponse(String id, String artistName, String artistImage) {
        this.id = id;
        this.artistName = artistName;
        this.artistImage = artistImage;
    }
}
