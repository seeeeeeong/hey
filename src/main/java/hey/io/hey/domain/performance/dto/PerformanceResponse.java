package hey.io.hey.domain.performance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.querydsl.core.annotations.QueryProjection;
import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.performance.domain.enums.PerformanceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "공연 목록 Response")
public class PerformanceResponse {


    @Schema(description = "공연 아이디")
    private String id;

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

    @Schema(description = "공연 포스터 이미지")
    private String poster;

    @Schema(description = "공연 상태 (UPCOMING / ONGOING / COMPLETED")
    private PerformanceStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;


    @QueryProjection
    public PerformanceResponse(String id, String title, LocalDate startDate, LocalDate endDate, String theater, String poster, PerformanceStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.theater = theater;
        this.poster = poster;
        this.status = status;
        this.createdAt = createdAt;
    }

    public PerformanceResponse(Performance performance) {
        this.id = performance.getId();
        this.title = performance.getTitle();
        this.startDate = performance.getStartDate();
        this.endDate = performance.getEndDate();
        this.theater = performance.getTheater();
        this.poster = performance.getPoster();
        this.status = performance.getStatus();
        this.createdAt = performance.getCreatedAt();
    }

    public static class Slice extends SliceImpl<PerformanceResponse> {
        public Slice(List<PerformanceResponse> content,
                     Pageable pageable, boolean hasNext) {
            super(content, pageable, hasNext);
        }
    }
}
