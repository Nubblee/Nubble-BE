package com.nubble.backend.codingproblem.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.codingproblem.controller.CodingProblemRequest.ProblemCreateRequest;
import com.nubble.backend.codingproblem.controller.CodingProblemResponse.ProblemCreateResponse;
import com.nubble.backend.codingproblem.service.CodingProblemCommand.ProblemCreateCommand;
import com.nubble.backend.codingproblem.service.CodingProblemService;
import com.nubble.backend.fixture.UserFixture;
import com.nubble.backend.session.domain.Session;
import com.nubble.backend.session.service.SessionRepository;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private CodingProblemService codingProblemService;

    @Autowired
    private CodingProblemCommandMapper problemCommandMapper;
    @Autowired
    private UserRepository userRepository;

    @DisplayName("코딩테스트 문제를 생성한다.")
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
        given(codingProblemService.createProblem(command))
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
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJson));
    }
}