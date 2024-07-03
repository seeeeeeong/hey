package hey.io.hey.domain.performance.service;

import hey.io.hey.common.config.QuerydslConfig;
import hey.io.hey.common.exception.BusinessException;
import hey.io.hey.common.fcm.service.FcmService;
import hey.io.hey.common.kopis.service.KopisService;
import hey.io.hey.common.response.SliceResponse;
import hey.io.hey.domain.performance.domain.BoxOfficeRank;
import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.performance.domain.PerformancePrice;
import hey.io.hey.domain.performance.domain.Place;
import hey.io.hey.domain.performance.domain.enums.PerformanceStatus;
import hey.io.hey.domain.performance.domain.enums.TimePeriod;
import hey.io.hey.domain.performance.dto.*;
import hey.io.hey.domain.performance.repository.BoxOfficeRankRepository;
import hey.io.hey.domain.performance.repository.PerformancePriceRepository;
import hey.io.hey.domain.performance.repository.PerformanceRepository;
import hey.io.hey.domain.performance.repository.PlaceRepository;
import hey.io.hey.domain.user.domain.User;
import hey.io.hey.domain.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = QuerydslConfig.class)
class PerformanceServiceTest {

    private PerformanceService performanceService;

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private BoxOfficeRankRepository boxOfficeRankRepository;

    @Autowired
    private PerformancePriceRepository performancePriceRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private KopisService kopisService;

    @Mock
    private FcmService fcmService;

    @BeforeEach
    void init() {
        performanceService = new PerformanceService(performancePriceRepository, performanceRepository, boxOfficeRankRepository, placeRepository, kopisService, fcmService);
    }

    @AfterEach
    void deleteAll() {
        boxOfficeRankRepository.deleteAll();
        performanceRepository.deleteAll();
        performancePriceRepository.deleteAll();;
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("getPerformanceByCondition - 성공")
    void getPerformancesByCondition_success() {
        // given
        Performance performance1 = createPerformance("1");
        Performance performance2 = createPerformance("2");
        Performance performance3 = createPerformance("3");
        Performance performance4 = createPerformance("4");

        performanceRepository.saveAll(List.of(performance1, performance2, performance3, performance4));

        // when
        PerformanceFilterRequest request = PerformanceFilterRequest.builder()
                .statuses(List.of(PerformanceStatus.ONGOING, PerformanceStatus.UPCOMING))
                .build();

        SliceResponse<PerformanceResponse> result = performanceService.getPerformancesByCondition(request, 20, 0, Sort.Direction.DESC);

        // then
        List<PerformanceResponse> contents = result.getContent();
        assertThat(contents).hasSize(4);
        assertThat(contents).extracting("id").containsExactly(performance4.getId(), performance3.getId(), performance2.getId(), performance1.getId());

    }

    @Test
    @DisplayName("searchPerformance - 성공")
    void searchPerformance_success() {
        // given
        Performance performance1 = createPerformance("1");
        Performance performance2 = createPerformance("2");
        Performance performance3 = createPerformance("3");
        Performance performance4 = createPerformance("4");

        performanceRepository.saveAll(List.of(performance1, performance2, performance3, performance4));

        // when
        PerformanceSearchRequest request = PerformanceSearchRequest.builder()
                .keyword("title")
                .build();

        SliceResponse<PerformanceResponse> result = performanceService.searchPerformances(request, 20, 0, Sort.Direction.DESC);

        // then
        List<PerformanceResponse> contents = result.getContent();
        assertThat(contents).hasSize(4);
        assertThat(contents).extracting("id").containsExactly(performance4.getId(), performance3.getId(), performance2.getId(), performance1.getId());
    }

    @Test
    @DisplayName("getNewPerformance - 성공")
    void getNewPerformance_success() {
        // given
        Performance performance1 = createPerformance("1");
        Performance performance2 = createPerformance("2");
        Performance performance3 = createPerformance("3");
        Performance performance4 = createPerformance("4");

        performanceRepository.saveAll(List.of(performance1, performance2, performance3, performance4));

        // when
        List<PerformanceResponse> result = performanceService.getNewPerformances();

        assertThat(result).hasSize(4);
        assertThat(result).extracting("id").containsExactly(performance4.getId(), performance3.getId(), performance2.getId(), performance1.getId());
    }


    @Test
    @DisplayName("getBoxOfficeRank - 성공")
    void getBoxOfficeRank_success() {
        // given
        Performance performance1 = createPerformance("1");
        Performance performance2 = createPerformance("2");
        Performance performance3 = createPerformance("3");
        Performance performance4 = createPerformance("4");

        performanceRepository.saveAll(List.of(performance1, performance2, performance3, performance4));

        BoxOfficeRank boxOfficeRank = BoxOfficeRank.builder()
                .performanceIds(performance1.getId() + "|" + performance2.getId() + "|" + performance3.getId() + "|" + performance4.getId())
                .timePeriod(TimePeriod.DAY)
                .build();

        boxOfficeRankRepository.save(boxOfficeRank);

        // when
        BoxOfficeRankRequest request = BoxOfficeRankRequest.builder()
                .timePeriod(TimePeriod.DAY)
                .build();


        List<PerformanceResponse> result = performanceService.getBoxOfficeRank(request);

        // then
        assertThat(result).hasSize(4);
        assertThat(result).extracting("id")
                .containsExactly(performance1.getId(), performance2.getId(), performance3.getId(), performance4.getId());
    }

    @Test
    @DisplayName("getBoxOfficeRank - 공연을 찾을 수 없습니다.")
    void getBoxOfficeRank_performanceNotFound() {
        // given
        // when
        BoxOfficeRankRequest request = BoxOfficeRankRequest.builder()
                .timePeriod(TimePeriod.DAY)
                .build();

        Throwable throwable = catchThrowable(() -> performanceService.getBoxOfficeRank(request));

        // then
        assertThat(throwable).isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("getPerformance - 성공")
    void getPerformance_success() {
        // given
        User user = User.create("email", "password");
        userRepository.save(user);

        double latitude = 0.1;
        double longitude = 0.2;

        Place place = Place.builder()
                .id("placeId")
                .latitude(latitude)
                .longitude(longitude)
                .address("address")
                .build();

        Place savedPlace = placeRepository.save(place);

        Performance performance = Performance.builder()
                .id("performanceId")
                .place(savedPlace)
                .title("title")
                .startDate(LocalDate.of(2023, 5, 1))
                .endDate(LocalDate.of(2023, 5, 2))
                .theater("theater")
                .cast("cast")
                .runtime("runtime")
                .age("age")
                .price("price")
                .poster("poster")
                .status(PerformanceStatus.ONGOING)
                .storyUrls("storyUrls")
                .schedule("schedule")
                .build();

        performanceRepository.save(performance);

        //when
        PerformanceDetailResponse result = performanceService.getPerformance(performance.getId());

        //then
        assertThat(result.getId()).isEqualTo(performance.getId());
        assertThat(result.getPlaceId()).isEqualTo(place.getId());
        assertThat(result.getTitle()).isEqualTo(performance.getTitle());
        assertThat(result.getStartDate()).isEqualTo(performance.getStartDate());
        assertThat(result.getEndDate()).isEqualTo(performance.getEndDate());
        assertThat(result.getTheater()).isEqualTo(performance.getTheater());
        assertThat(result.getCast()).isEqualTo(performance.getCast());
        assertThat(result.getRuntime()).isEqualTo(performance.getRuntime());
        assertThat(result.getAge()).isEqualTo(performance.getAge());
        assertThat(result.getPrice()).isEqualTo(performance.getPrice());
        assertThat(result.getPoster()).isEqualTo(performance.getPoster());
        assertThat(result.getStatus()).isEqualTo(performance.getStatus());
        assertThat(result.getStoryUrls()).hasSize(1);
        assertThat(result.getSchedule()).isEqualTo(performance.getSchedule());
        assertThat(result.getLatitude()).isEqualTo(place.getLatitude());
        assertThat(result.getLongitude()).isEqualTo(place.getLongitude());
        assertThat(result.getAddress()).isEqualTo(place.getAddress());
    }


    @Test
    @DisplayName("getPerformance - 공연을 찾을 수 없습니다.")
    void getPerformance_performanceNotFound() {
        // given
        User user = User.create("email", "password");
        userRepository.save(user);
        // when
        Throwable throwable = catchThrowable(() -> performanceService.getPerformance("randomId"));

        // then
        assertThat(throwable).isInstanceOf(BusinessException.class);
    }

    private Performance createPerformance(String id) {
        return Performance.builder()
                .id(id)
                .title("title")
                .status(PerformanceStatus.ONGOING)
                .build();
    }

    private PerformancePrice createPerformancePrice(Performance performance, int price) {
        return PerformancePrice.builder()
                .performance(performance)
                .price(price)
                .build();
    }
}