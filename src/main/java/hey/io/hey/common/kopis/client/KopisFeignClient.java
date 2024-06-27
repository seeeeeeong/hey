package hey.io.hey.common.kopis.client;


import hey.io.hey.common.config.FeignConfig;
import hey.io.hey.common.kopis.client.dto.*;
import hey.io.hey.kopis.client.dto.*;
import hey.io.hey.module.kopis.client.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "kopisFeignClient", url = "${kopis.performance.url:url}", configuration = FeignConfig.class)
public interface KopisFeignClient {

    @GetMapping(value = "/pblprfr", produces = "application/xml;charset=UTF-8")
    List<KopisPerformanceResponse> getPerformances(@SpringQueryMap KopisPerformanceRequest request, @RequestParam("service") String apiKey);

    @GetMapping(value = "/pblprfr/{performanceId}", produces = "application/xml;charset=UTF-8")
    List<KopisPerformanceDetailResponse> getPerformanceDetail(@PathVariable("performanceId") String performanceId, @RequestParam("service") String apiKey);

    @GetMapping(value = "/prfplc/{placeId}", produces = "application/xml;charset=UTF-8")
    List<KopisPlaceDetailResponse> getPlaceDetail(@PathVariable("placeId") String placeId, @RequestParam("service") String apiKey);

    @GetMapping(value = "/boxoffice", produces = "application/xml;charset=UTF-8")
    List<KopisBoxOfficeResponse> getBoxOffice(@SpringQueryMap KopisBoxOfficeRequest request, @RequestParam("service") String apiKey);
}
