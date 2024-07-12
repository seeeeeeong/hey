package hey.io.hey.domain.album.dto;

import com.querydsl.core.annotations.QueryProjection;
import hey.io.hey.domain.album.domain.AlbumEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(description = "앨범 Response")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlbumResponse {

    @Schema(description = "앨범 ID")
    private String id;

    @Schema(description = "앨범 타이틀")
    private String title;

    @Schema(description = "앨범 이미지")
    private String albumImage;

    @Schema(description = "앨범 발매일")
    private String releaseDate;

    @QueryProjection
    public AlbumResponse(AlbumEntity album) {
        this.id = album.getId();
        this.title = album.getTitle();
        this.albumImage = album.getAlbumImage();
        this.releaseDate = album.getReleaseDate();
    }

}
