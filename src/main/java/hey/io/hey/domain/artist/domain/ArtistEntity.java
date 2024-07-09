package hey.io.hey.domain.artist.domain;

import hey.io.hey.common.entity.BaseEntityWithUpdate;
import hey.io.hey.domain.album.domain.AlbumEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtistEntity extends BaseEntityWithUpdate {

    @Id
    private String id;

    private String artistName;
    private String artistImage;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> genre = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artist", cascade = CascadeType.ALL)
    private List<AlbumEntity> albums = new ArrayList<>();

    @NotNull
    private LocalDateTime createdAt;

    @Builder(toBuilder = true)
    private ArtistEntity(String id, String artistName, String artistImage, List<String> genre, LocalDateTime createdAt) {
        this.id = id;
        this.artistName = artistName;
        this.artistImage = artistImage;
        this.genre = genre;
        this.createdAt = createdAt;
    }

    public static ArtistEntity of(String id, String artistName, String artistImage, List<String> genre) {
        return ArtistEntity.builder()
                .id(id)
                .artistName(artistName)
                .artistImage(artistImage)
                .genre(genre)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void addAlbum(AlbumEntity album) {
        this.albums.add(album);
    }

}
