package hey.io.hey.kopis.client.dto;

import hey.io.hey.domain.performance.domain.Place;

public record KopisPlaceDetailResponse(
        String fcltynm,
        String mt10id,
        String adres,
        Double la,
        Double lo
) {

    public Place toEntity() {
        return Place.builder()
                .id(mt10id)
                .name(fcltynm)
                .address(adres)
                .latitude(la)
                .longitude(lo)
                .build();
    }

}
