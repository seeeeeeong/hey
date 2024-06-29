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
public class PerformanceResponse {

    private String id;
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
    private String poster;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;


    @QueryProjection
    public PerformanceResponse(String id, String title, LocalDate startDate, LocalDate endDate, String theater, String poster, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.theater = theater;
        this.poster = poster;
        this.createdAt = createdAt;
    }

    public PerformanceResponse(Performance performance) {
        this.id = performance.getId();
        this.title = performance.getTitle();
        this.startDate = performance.getStartDate();
        this.endDate = performance.getEndDate();
        this.theater = performance.getTheater();
        this.poster = performance.getPoster();
        this.createdAt = performance.getCreatedAt();
    }

    public static class Slice extends SliceImpl<PerformanceResponse> {
        public Slice(List<PerformanceResponse> content,
                     Pageable pageable, boolean hasNext) {
            super(content, pageable, hasNext);
        }
    }
}
