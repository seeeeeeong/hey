package hey.io.hey.domain.album.dto;

import hey.io.hey.domain.album.domain.AlbumEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlbumResponse {

    private String id;
    private String title;
    private String albumImage;
    private String releaseDate;

    public AlbumResponse(AlbumEntity album) {
        this.id = album.getId();
        this.title = album.getTitle();
        this.albumImage = album.getAlbumImage();
        this.releaseDate = album.getReleaseDate();
    }

}
