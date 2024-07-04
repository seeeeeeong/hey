package hey.io.hey.domain.performance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import hey.io.hey.domain.performance.domain.enums.PerformanceStatus;
import lombok.*;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceDetailResponse {

    private String id;
    private String placeId;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;
    private String theater;
    private String cast;
    private String runtime;
    private String age;
    private String price;
    private String poster;
    private boolean visit;
    private PerformanceStatus status;
    private List<String> storyUrls;
    private String schedule;
    private Double latitude;
    private Double longitude;
    private String address;

    public void updateStoryUrls(String storyUrls) {
        if (StringUtils.hasText(storyUrls)) {
            this.storyUrls = List.of(storyUrls.split("\\|"));
        } else {
            this.storyUrls = Collections.emptyList();
        }
    }

    public void updateLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
