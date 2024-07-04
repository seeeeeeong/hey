package hey.io.hey.domain.follow.service;

import hey.io.hey.common.config.QuerydslConfig;
import hey.io.hey.common.exception.BusinessException;
import hey.io.hey.common.response.SliceResponse;
import hey.io.hey.domain.follow.domain.Follow;
import hey.io.hey.domain.follow.dto.FollowResponse;
import hey.io.hey.domain.follow.repository.FollowRepository;
import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.performance.domain.enums.PerformanceStatus;
import hey.io.hey.domain.performance.dto.PerformanceResponse;
import hey.io.hey.domain.performance.repository.PerformanceRepository;
import hey.io.hey.domain.user.domain.SocialCode;
import hey.io.hey.domain.user.domain.User;
import hey.io.hey.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = QuerydslConfig.class)
class FollowServiceTest {

    private FollowService followService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private FollowRepository followRepository;

    @BeforeEach
    void init() {
        followService = new FollowService(userRepository, performanceRepository, followRepository);
    }

    @AfterEach
    void deleteAll() {
        userRepository.deleteAll();;
        performanceRepository.deleteAll();
        followRepository.deleteAll();
    }

    @Test
    @DisplayName("follow - 성공")
    void follow_success() {
        // given
        User user = User.create("email", SocialCode.GOOGLE);
        userRepository.save(user);
        Performance performance1 = createPerformance("1");
        performanceRepository.save(performance1);

        // when
        FollowResponse result = followService.follow(user.getUserId(), performance1.getId());

        // then
        assertEquals(result.getPerformanceId(), performance1.getId());
        assertEquals(result.getMessage(), "Follow Success");
    }

    @Test
    @DisplayName("follow - 유저를 찾을 수 없습니다.")
    void follow_userNotFound() {
        // given
        User user = User.create("email", SocialCode.GOOGLE);
        userRepository.save(user);
        Performance performance1 = createPerformance("1");
        performanceRepository.save(performance1);

        // when
        Throwable throwable = catchThrowable(() -> followService.follow(2L, performance1.getId()));

        // then
        Assertions.assertThat(throwable).isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("follow - 공연을 찾을 수 없습니다.")
    void follow_performanceNotFound() {
        // given
        User user = User.create("email", SocialCode.GOOGLE);
        userRepository.save(user);
        Performance performance1 = createPerformance("1");
        performanceRepository.save(performance1);

        // when
        Throwable throwable = catchThrowable(() -> followService.follow(user.getUserId(), "2"));

        // then
        Assertions.assertThat(throwable).isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("follow - 이미 팔로우한 공연입니다.")
    void follow_alreadyFollowedPerformance() {
        // given
        User user = User.create("email", SocialCode.GOOGLE);
        userRepository.save(user);
        Performance performance1 = createPerformance("1");
        performanceRepository.save(performance1);
        Follow follow = Follow.of(user, performance1);
        followRepository.save(follow);

        // when
        Throwable throwable = catchThrowable(() -> followService.follow(user.getUserId(), performance1.getId()));

        // then
        Assertions.assertThat(throwable).isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("getFollow - 성공")
    void getFollow_success() {
        // given
        User user = User.create("email", SocialCode.GOOGLE);
        userRepository.save(user);
        Performance performance1 = createPerformance("1");
        performanceRepository.save(performance1);
        Follow follow = Follow.of(user, performance1);
        followRepository.save(follow);

        // when
        SliceResponse<PerformanceResponse> result = followService.getFollow(user.getUserId(), 20, 0, Sort.Direction.DESC);

        // then
        List<PerformanceResponse> contents = result.getContent();
        Assertions.assertThat(contents).hasSize(1);
        Assertions.assertThat(contents).extracting("id").containsExactly(performance1.getId());
    }

    @Test
    @DisplayName("getFollow - 유저를 찾을 수 없습니다.")
    void getFollow_userNotFound() {
        // given
        User user = User.create("email", SocialCode.GOOGLE);
        userRepository.save(user);
        Performance performance1 = createPerformance("1");
        performanceRepository.save(performance1);
        Follow follow = Follow.of(user, performance1);
        followRepository.save(follow);

        // when
        Throwable throwable = catchThrowable(() -> followService.getFollow(2L, 20, 0, Sort.Direction.DESC));

        // then
        Assertions.assertThat(throwable).isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("deleteFollow - 성공")
    void deleteFollow_success() {
        // given
        User user = User.create("email", SocialCode.GOOGLE);
        userRepository.save(user);
        Performance performance1 = createPerformance("1");
        performanceRepository.save(performance1);
        Follow follow = Follow.of(user, performance1);
        followRepository.save(follow);

        // when
        FollowResponse result = followService.deleteFollow(user.getUserId(), performance1.getId());

        // then
        assertEquals(result.getPerformanceId(), performance1.getId());
        assertEquals(result.getMessage(), "UnFollow Success");
    }

    @Test
    @DisplayName("deleteFollow - 유저를 찾을 수 없습니다.")
    void deleteFollow_userNotFound() {
        // given
        User user1 = User.create("email", SocialCode.GOOGLE);
        userRepository.save(user1);
        User user2 = User.create("email2", SocialCode.GOOGLE);
        userRepository.save(user2);
        Performance performance1 = createPerformance("1");
        performanceRepository.save(performance1);
        Follow follow = Follow.of(user1, performance1);
        followRepository.save(follow);

        // when
        Throwable throwable = catchThrowable(() -> followService.deleteFollow(user2.getUserId(), performance1.getId()));

        // then
        Assertions.assertThat(throwable).isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("deleteFollow - 공연을 찾을 수 없습니다.")
    void deleteFollow_performanceNotFound() {
        // given
        User user = User.create("email", SocialCode.GOOGLE);
        userRepository.save(user);
        Performance performance1 = createPerformance("1");
        performanceRepository.save(performance1);
        Follow follow = Follow.of(user, performance1);
        followRepository.save(follow);

        // when
        Throwable throwable = catchThrowable(() -> followService.deleteFollow(user.getUserId(), "2"));

        // then
        Assertions.assertThat(throwable).isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("deleteFollow - 팔로잉을 찾을 수 없습니다.")
    void deleteFollow_followNotFound() {
        // given
        User user = User.create("email", SocialCode.GOOGLE);
        userRepository.save(user);
        Performance performance1 = createPerformance("1");
        performanceRepository.save(performance1);

        // when
        Throwable throwable = catchThrowable(() -> followService.deleteFollow(user.getUserId(), "2"));

        // then
        Assertions.assertThat(throwable).isInstanceOf(BusinessException.class);
    }

    private Performance createPerformance(String id) {
        return Performance.builder()
                .id(id)
                .title("title")
                .status(PerformanceStatus.ONGOING)
                .build();
    }

}