package hey.io.hey.domain.performance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import hey.io.hey.domain.performance.domain.enums.PerformanceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "공연 상세 Response")
public class PerformanceDetailResponse {

    @Schema(description = "공연 ID")
    private String id;

    @Schema(description = "공연 장소 ID")
    private String placeId;

    @Schema(description = "공연명")
    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Schema(description = "공연 시작일")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Schema(description = "공연 종료일")
    private LocalDate endDate;

    @Schema(description = "공연장")
    private String theater;

    @Schema(description = "출연진")
    private String cast;

    @Schema(description = "러닝 타임")
    private String runtime;

    @Schema(description = "관람 연령")
    private String age;

    @Schema(description = "가격")
    private String price;

    @Schema(description = "포스터 이미지")
    private String poster;

    @Schema(description = "내한 여부")
    private Boolean visit;

    @Schema(description = "공연 상태 (공연 예정 / 공연 중 / 공연 완료")
    private PerformanceStatus status;

    @Schema(description = "상세 이미지")
    private List<String> storyUrls;

    @Schema(description = "공연 시간")
    private String schedule;

    @Schema(description = "위도")
    private Double latitude;

    @Schema(description = "경도")
    private Double longitude;

    @Schema(description = "주소")
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
