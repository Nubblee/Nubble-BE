package com.nubble.backend.session.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.config.properties.SessionCookieProperties;
import com.nubble.backend.session.controller.SessionRequest.SessionIssueRequest;
import com.nubble.backend.session.service.SessionCommand.SessionCreateCommand;
import com.nubble.backend.session.service.SessionInfo;
import com.nubble.backend.session.service.SessionService;
import jakarta.servlet.http.Cookie;
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

@SpringBootTest
@AutoConfigureMockMvc
class SessionApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SessionService sessionService;

    @Autowired
    private SessionCommandMapper sessionCommandMapper;

    @Autowired
    private SessionCookieProperties sessionCookieProperties;

    @DisplayName("아이디와 비밀번호가 매칭되면 세션쿠키를 발급합니다.")
    @Test
    void issue_Session_success() throws Exception {
        // given
        SessionIssueRequest request = SessionIssueRequest.builder()
                .username("user")
                .password("1234")
                .build();
        SessionCreateCommand command = sessionCommandMapper.fromRequest(request);

        String sessionId = UUID.randomUUID().toString();
        SessionInfo info = SessionInfo.builder()
                .sessionId(sessionId)
                .build();
        given(sessionService.createSession(command))
                .willReturn(info);

        MockHttpServletRequestBuilder requestBuilder = post("/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        // when & then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(cookie().value(sessionCookieProperties.getName(), sessionId))
                .andDo(print());
    }

    @DisplayName("유효한 세션을 가지고 있을 경우, ok를 반환합니다.")
    @Test
    void validateSession() throws Exception {
        // given
        String validSessionId = UUID.randomUUID().toString();
        MockHttpServletRequestBuilder requestBuilder = get("/sessions/validate")
                .cookie(new Cookie(sessionCookieProperties.getName(), validSessionId));

        // when & then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print());
    }
}
