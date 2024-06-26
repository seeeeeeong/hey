package hey.io.hey.module.kopis.client.dto;

import hey.io.hey.domain.place.domain.Place;

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
