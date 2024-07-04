package hey.io.hey.domain.performance.domain;

import hey.io.hey.common.entity.BaseEntityWithUpdate;
import hey.io.hey.domain.performance.domain.enums.PerformanceStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "performance", indexes = {
        @Index(name = "idx_performance_id", columnList = "id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Performance extends BaseEntityWithUpdate {

    @Id
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String theater;
    @Column(name = "`cast`")
    private String cast;
    private String runtime;
    private String age;
    private String price;
    private String poster;
    private boolean visit;
    @Enumerated(EnumType.STRING)
    private PerformanceStatus status;
    @Column(name = "story_urls", columnDefinition = "TEXT")
    private String storyUrls;
    private String schedule;

    @Builder(toBuilder = true)
    private Performance(String id, Place place, String title, LocalDate startDate, LocalDate endDate,
                        String theater, String cast, String runtime, String age, String price,
                        String poster, boolean visit, PerformanceStatus status, String storyUrls, String schedule) {
        this.id = id;
        this.place = place;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.theater = theater;
        this.cast = cast;
        this.runtime = runtime;
        this.age = age;
        this.price = price;
        this.poster = poster;
        this.visit = visit;
        this.status = status;
        this.storyUrls = storyUrls;
        this.schedule = schedule;
    }


    public void updateStatus(PerformanceStatus status) {
        this.status = status;
    }

    public void updatePlace(Place place) {
        this.place = place;
    }

}
