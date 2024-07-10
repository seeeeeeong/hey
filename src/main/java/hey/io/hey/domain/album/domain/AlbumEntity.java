package hey.io.hey.domain.album.domain;

import hey.io.hey.common.entity.BaseEntityWithUpdate;
import hey.io.hey.domain.artist.domain.ArtistEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlbumEntity extends BaseEntityWithUpdate {

    @Id
    private String id;
    private String title;
    private String albumImage;
    private String releaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private ArtistEntity artist;

    @NotNull
    private LocalDateTime createdAt;

    @Builder
    private AlbumEntity(String id, String title, String albumImage, String releaseDate, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.albumImage = albumImage;
        this.releaseDate = releaseDate;
        this.createdAt = createdAt;
    }

    public static AlbumEntity of(String id, String title, String albumImage, String releaseDate) {
        return AlbumEntity.builder()
                .id(id)
                .title(title)
                .albumImage(albumImage)
                .releaseDate(releaseDate)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void setArtist(ArtistEntity artist) {
        this.artist = artist;
    }

}
