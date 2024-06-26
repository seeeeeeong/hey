package hey.io.hey.domain.performance.service;

import hey.io.hey.common.exception.BusinessException;
import hey.io.hey.common.exception.ErrorCode;
import hey.io.hey.domain.performance.domain.PerformancePrice;
import hey.io.hey.domain.performance.domain.enums.PerformanceStatus;
import hey.io.hey.domain.performance.repository.PerformancePriceRepository;
import hey.io.hey.domain.place.repository.PlaceRepository;
import hey.io.hey.module.kopis.client.dto.KopisPerformanceDetailResponse;
import hey.io.hey.module.kopis.client.dto.KopisPerformanceRequest;
import hey.io.hey.module.kopis.client.dto.KopisPerformanceResponse;
import hey.io.hey.common.response.SliceResponse;
import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.performance.dto.*;
import hey.io.hey.domain.performance.repository.PerformanceRepository;
import hey.io.hey.domain.place.domain.Place;
import hey.io.hey.module.kopis.client.dto.KopisPlaceDetailResponse;
import hey.io.hey.module.kopis.service.KopisService;
import hey.io.hey.module.mapper.PerformanceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformanceService {

    private final PerformancePriceRepository performancePriceRepository;
    private final PerformanceRepository performanceRepository;
    private final PlaceRepository placeRepository;
    private final KopisService kopisService;


    public SliceResponse<PerformanceResponse> getPerformancesByCondition(PerformanceFilterRequest request, int size, int page, Direction direction) {
        Slice<PerformanceResponse> performances = performanceRepository.getPerformancesByCondition(request, Pageable.ofSize(size).withPage(page), direction);
        return new SliceResponse<>(performances);
    }

    public SliceResponse<PerformanceResponse> searchPerformances(PerformanceSearchRequest request, int size, int page, Direction direction) {
        Slice<PerformanceResponse> performances = performanceRepository.searchPerformances(request, Pageable.ofSize(size).withPage(page), direction);
        return new SliceResponse<>(performances);
    }

    public List<PerformanceResponse> getNewPerformances() {
        List<Performance> newPerformances = performanceRepository.findTop5ByOrderByCreatedAtDesc();

        return newPerformances.stream()
                .map(PerformanceResponse::new)
                .collect(Collectors.toList());
    }

    public PerformanceDetailResponse getPerformance(String id) {
        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PERFORMANCE_NOT_FOUND));

        return getPerformanceDetailResponse(performance);

    }

    @Transactional
    public int updatePerformancesBatch(LocalDate from, LocalDate to, int rows) {
        log.info("[Batch] Batch Updating Performances...");
        KopisPerformanceRequest kopisPerformanceRequest = KopisPerformanceRequest.builder()
                .stdate(from.format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .eddate(to.format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .cpage(1)
                .rows(rows)
                .shcate("CCCD")
                .build();

        List<KopisPerformanceResponse> kopisPerformanceResponseList = kopisService.getPerformances(kopisPerformanceRequest);

        List<String> allIdList = performanceRepository.findAllIds();
        HashSet<String> allIdSet = new HashSet<>(allIdList);

        List<Performance> newPerformances = new ArrayList<>();

        for (KopisPerformanceResponse kopisPerformanceResponse : kopisPerformanceResponseList) {
            String performanceId = kopisPerformanceResponse.mt20id();
            if (!allIdSet.contains(performanceId)) {
                KopisPerformanceDetailResponse kopisPerformanceDetailResponse = kopisService.getPerformanceDetail(performanceId);
                Performance performance = kopisPerformanceDetailResponse.toEntity();
                String placeId = kopisPerformanceDetailResponse.mt10id();
                KopisPlaceDetailResponse kopisPlaceDetailResponse = kopisService.getPlaceDetail(placeId);
                Place place = kopisPlaceDetailResponse.toEntity();
                placeRepository.save(place);
                performance.updatePlace(place);
                newPerformances.add(performance);
            }
        }

        List<Performance> performances = performanceRepository.saveAll(newPerformances);
        List<PerformancePrice> performancePrices = performances.stream()
                .filter(performance -> StringUtils.hasText(performance.getPrice()))
                .flatMap(performance -> getPerformancePrice(performance).stream())
                .collect(Collectors.toList());

        performancePriceRepository.saveAll(performancePrices);
        log.info("[Batch] Performance has benn Updated... Total Size : {}", kopisPerformanceResponseList.size(), newPerformances.size());
        return performances.size();
    }

    @Transactional
    public int updatePerformanceStatusBatch() {
        log.info("[Batch] Batch Updating Performance status");
        List<Performance> performanceList = performanceRepository.findAll();
        int updateCnt = 0;
        for (Performance performance : performanceList) {
            LocalDate today = LocalDate.now();
            LocalDate startDate = performance.getStartDate();
            LocalDate endDate = performance.getEndDate();
            PerformanceStatus performanceStatus;
            if (today.isAfter(endDate)) {
                performanceStatus = PerformanceStatus.COMPLETED;
            } else if (today.isBefore(startDate)) {
                performanceStatus = PerformanceStatus.UPCOMING;
            } else {
                performanceStatus = PerformanceStatus.ONGOING;
            }

            if (performance.getStatus() != performanceStatus) {
                performance.updateStatus(performanceStatus);
                updateCnt++;
            }
        }
        log.info("[Batch] Performance State has been Updated... Updated Count : {}", updateCnt);
        return updateCnt;
    }

    private PerformanceDetailResponse getPerformanceDetailResponse(Performance performance) {
        PerformanceDetailResponse performanceDetailResponse = PerformanceMapper.INSTANCE.toPerformanceDto(performance);
        performanceDetailResponse.updateStoryUrls(performance.getStoryUrls());

        Place place = performance.getPlace();
        if (!ObjectUtils.isEmpty(place)) {
            performanceDetailResponse.updateLocation(place.getLatitude(), place.getLongitude());
            performanceDetailResponse.setAddress(place.getAddress());
            performanceDetailResponse.setPlaceId(place.getId());
        }
        return performanceDetailResponse;
    }

    private List<PerformancePrice> getPerformancePrice(Performance performance) {
        String price = performance.getPrice();
        String replacedPrice = price.replace(",", "");
        String[] splitString = replacedPrice.split(" ");

        return Arrays.stream(splitString)
                .filter(str -> str.endsWith("00원"))
                .map(str -> PerformancePrice.builder()
                        .price(Integer.parseInt(str.substring(0, str.length() - 1)))
                        .performance(performance)
                        .build())
                .collect(Collectors.toList());
    }

}
