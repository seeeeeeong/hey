package hey.io.hey.domain.follow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hey.io.hey.common.response.SliceResponse;
import hey.io.hey.common.security.jwt.JwtTokenProvider;
import hey.io.hey.domain.follow.dto.FollowResponse;
import hey.io.hey.domain.follow.service.FollowService;
import hey.io.hey.domain.performance.controller.PerformanceController;
import hey.io.hey.domain.performance.dto.PerformanceDetailResponse;
import hey.io.hey.domain.performance.dto.PerformanceResponse;
import hey.io.hey.domain.user.domain.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Constructor;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FollowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FollowService followService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FollowController(followService)).build();
    }
    @Test
    @WithMockUser
    void follow() throws Exception {
        // given
        Long userId = 1L;
        String accessToken = "Bearer " + jwtTokenProvider.createAccessToken(userId, UserRole.USER);

        String performanceId = "performanceId";

        FollowResponse followResponse = new FollowResponse(performanceId, "Follow Success");

        // when
        when(followService.follow(userId, performanceId)).thenReturn(followResponse);
        ResultActions result = mockMvc.perform(post("/users/follow/{id}", performanceId)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getFollow() throws Exception {
        // given
        Long userId = 1L;
        String accessToken = "Bearer " + jwtTokenProvider.createAccessToken(userId, UserRole.USER);

        String performanceId = "performanceId";

        SliceResponse<PerformanceResponse> sliceResponse = new SliceResponse<>(new SliceImpl<>(Collections.emptyList(), PageRequest.of(0, 20), false));

        // when
        when(followService.getFollow(any(), eq(20), eq(0), eq(Sort.Direction.DESC))).thenReturn(sliceResponse);
        ResultActions result = mockMvc.perform(get("/users/follow")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void deleteFollow() throws Exception {
        // given
        Long userId = 1L;
        String accessToken = "Bearer " + jwtTokenProvider.createAccessToken(userId, UserRole.USER);

        String performanceId = "performanceId";

        FollowResponse followResponse = new FollowResponse(performanceId, "UnFollow Success");

        // when
        when(followService.follow(userId, performanceId)).thenReturn(followResponse);
        ResultActions result = mockMvc.perform(delete("/users/follow/{id}", performanceId)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk());
    }
}