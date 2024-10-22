package com.nubble.backend.codingproblem.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.codingproblem.controller.CodingProblemRequest.ProblemCreateRequest;
import com.nubble.backend.codingproblem.controller.CodingProblemResponse.ProblemCreateResponse;
import com.nubble.backend.codingproblem.controller.CodingProblemResponse.ProblemSearchResponse;
import com.nubble.backend.codingproblem.service.CodingProblemCommand.ProblemCreateCommand;
import com.nubble.backend.codingproblem.service.CodingProblemCommand.ProblemSearchCommand;
import com.nubble.backend.codingproblem.service.CodingProblemInfo;
import com.nubble.backend.codingproblem.service.CodingProblemService;
import com.nubble.backend.fixture.domain.UserFixture;
import com.nubble.backend.user.session.domain.Session;
import com.nubble.backend.user.session.service.SessionRepository;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest // todo 컨트롤러 테스트 springbootTest 분리하기
@AutoConfigureMockMvc
@Transactional
class CodingProblemApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SessionRepository sessionRepository;

    @MockBean
    private CodingProblemService problemService;

    @Autowired
    private CodingProblemCommandMapper problemCommandMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CodingProblemResponseMapper problemResponseMapper;

    @DisplayName("로그인한 유저가 코딩테스트 문제를 등록한다.")
    @Test
    void createProblem_success() throws Exception {
        // given
        User user = UserFixture.aUser().build();
        userRepository.save(user);
        Session session = Session.builder()
                .user(user)
                .accessId(UUID.randomUUID().toString())
                .expireAt(LocalDateTime.now().plusDays(1))
                .build();
        sessionRepository.save(session);

        ProblemCreateRequest request = ProblemCreateRequest.builder()
                .quizDate(LocalDate.now())
                .problemTitle("새로운 문제")
                .problemUrl("https:")
                .build();

        ProblemCreateCommand command = problemCommandMapper.of(request, user.getId());
        long newProblemId = 1L;
        given(problemService.createProblem(command))
                .willReturn(newProblemId);

        MockHttpServletRequestBuilder requestBuilder = post("/coding-problems")
                .header("SESSION-ID", session.getAccessId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        ProblemCreateResponse expectedResponse = ProblemCreateResponse.builder()
                .problemId(newProblemId)
                .build();
        String responseJson = objectMapper.writeValueAsString(expectedResponse);

        // when & then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJson))
                .andDo(print());
    }

    @DisplayName("로그인한 유저가 코딩테스트 문제를 삭제한다.")
    @Test
    void test() throws Exception {
        // given
        // todo UserSession 발급의 중복이 발생, 한 번에 처리하는 클래스 필요
        User user = UserFixture.aUser().build();
        userRepository.save(user);
        Session session = Session.builder()
                .user(user)
                .accessId(UUID.randomUUID().toString())
                .expireAt(LocalDateTime.now().plusDays(1))
                .build();
        sessionRepository.save(session);

        Long problemId = 1L;
        MockHttpServletRequestBuilder requestBuilder = delete("/coding-problems/{problemId}", problemId)
                .header("SESSION-ID", session.getAccessId());

        // when & then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @DisplayName("파라미터에 맞는 코딩테스트 문제들을 가져올 수 있다.")
    @Test
    void searchProblems() throws Exception {
        // given
        CodingProblemInfo info1 = CodingProblemInfo.builder()
                .problemId(1L)
                .quizDate(LocalDate.now())
                .problemTitle("퇴사 2")
                .url("https://www.acmicpc.net/problem/15486")
                .build();
        CodingProblemInfo info2 = CodingProblemInfo.builder()
                .problemId(2L)
                .quizDate(LocalDate.now())
                .problemTitle("내리막 길")
                .url("https://www.acmicpc.net/problem/1520")
                .build();
        List<CodingProblemInfo> infos = List.of(info1, info2);

        given(problemService.searchProblems(ProblemSearchCommand.builder().build()))
                .willReturn(infos);

        ProblemSearchResponse responses = problemResponseMapper.toProblemSearchResponse(infos);
        String responsesJson = objectMapper.writeValueAsString(responses);

        MockHttpServletRequestBuilder requestBuilder = get("/coding-problems");
        // when & then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(responsesJson))
                .andDo(print());
    }
}
