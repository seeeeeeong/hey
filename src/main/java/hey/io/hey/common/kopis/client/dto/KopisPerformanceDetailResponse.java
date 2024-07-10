package hey.io.hey.common.kopis.client.dto;

import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.performance.domain.enums.PerformanceStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record KopisPerformanceDetailResponse(
        String mt20id, // 공연 ID
        String mt10id, // 공연시설 ID
        String prfnm, // 공연명
        String prfpdfrom, // 공연 시작일
        String prfpdto, // 공연 종료일
        String fcltynm, // 공연 시설명
        String prfcast, // 공연 출연진
        String prfruntime, // 공연 런타임
        String prfage, // 공연 관람 연령
        String pcseguidance, // 티켓 가격
        String poster, // 포스터 이미지경로
        String visit, // 국내, 내한
        String prfstate, // 공연 상태
        String[] styurls, // 소개이미지 목록
        String dtguidance // 공연 시간
) {
    public Performance toEntity() {

        return Performance.builder()
                .id(this.mt20id)
                .title(this.prfnm)
                .startDate(LocalDate.parse(this.prfpdfrom, DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .endDate(LocalDate.parse(this.prfpdto, DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .theater(this.fcltynm)
                .cast(this.prfcast.equals(" ") ? null : this.prfcast)
                .runtime(this.prfruntime.equals(" ") ? null : this.prfruntime)
                .age(this.prfage.equals(" ") ? null : this.prfage)
                .price(this.pcseguidance.equals(" ") ? null : this.pcseguidance)
                .poster(this.poster.equals(" ") ? null : this.poster)
                .schedule(this.dtguidance.equals(" ") ? null : this.dtguidance)
                .visit(this.visit.equals("Y"))
                .status(PerformanceStatus.getByName(this.prfstate))
                .storyUrls(
                        this.styurls != null ?
                                String.join("|", this.styurls) : null
                )
                .build();
    }
}
