package hey.io.hey.module.kopis.service;

import hey.io.hey.module.kopis.client.KopisFeignClient;
import hey.io.hey.module.kopis.client.dto.KopisPerformanceDetailResponse;
import hey.io.hey.module.kopis.client.dto.KopisPerformanceRequest;
import hey.io.hey.module.kopis.client.dto.KopisPerformanceResponse;
import hey.io.hey.module.kopis.client.dto.KopisPlaceDetailResponse;
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
        KopisPerformanceDetailResponse kopisPerformanceDetailResponse = kopisFeignClient.getPerformanceDetail(performanceId, apiKey).get(0);
        if (kopisPerformanceDetailResponse.mt20id() == null) {
            throw new IllegalStateException("Fail to GET Performance Detail..");
        }
        return kopisPerformanceDetailResponse;
    }

    public KopisPlaceDetailResponse getPlaceDetail(String placeId) {
        return kopisFeignClient.getPlaceDetail(placeId, apiKey).get(0);
    }
}
