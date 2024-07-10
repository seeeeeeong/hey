package hey.io.hey.common.kopis.service;

import hey.io.hey.common.kopis.client.KopisFeignClient;
import hey.io.hey.common.kopis.client.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KopisService {

    @Value("${kopis.api.key}")
    private String apiKey;


    private final KopisFeignClient kopisFeignClient;

    public List<KopisPerformanceResponse> getPerformances(KopisPerformanceRequest kopisPerformanceRequest) {
        return kopisFeignClient.getPerformances(kopisPerformanceRequest, apiKey);
    }

    public KopisPerformanceDetailResponse getPerformanceDetail(String performanceId) {
        KopisPerformanceDetailResponse kopisPerformanceDetailResponse = kopisFeignClient.getPerformanceDetail(performanceId, apiKey, "Y").get(0);
        if (kopisPerformanceDetailResponse.mt20id() == null) {
            throw new IllegalStateException("Fail to GET Performance Detail..");
        }
        return kopisPerformanceDetailResponse;
    }

    public List<KopisBoxOfficeResponse> getBoxOffice(KopisBoxOfficeRequest kopisBoxOfficeRequest) {
        return kopisFeignClient.getBoxOffice(kopisBoxOfficeRequest, apiKey);
    }

    public KopisPlaceDetailResponse getPlaceDetail(String placeId) {
        return kopisFeignClient.getPlaceDetail(placeId, apiKey).get(0);
    }
}
