package hey.io.hey.domain.performance.domain;

import hey.io.hey.common.entity.BaseEntityWithUpdate;
import hey.io.hey.domain.artist.domain.ArtistEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import se.michaelthelin.spotify.model_objects.specification.Artist;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceArtist extends BaseEntityWithUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private ArtistEntity artist;

    @Builder
    private PerformanceArtist(Performance performance, ArtistEntity artist) {
        this.performance = performance;
        this.artist = artist;
    }

    public static PerformanceArtist of(Performance performance, ArtistEntity artist) {
        return PerformanceArtist.builder()
                .performance(performance)
                .artist(artist)
                .build();
    }

}
