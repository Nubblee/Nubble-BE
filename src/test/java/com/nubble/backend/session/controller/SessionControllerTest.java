package com.nubble.backend.session.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.session.controller.SessionRequest.Issuance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@WebMvcTest(SessionController.class)
class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("아이디와 비밀번호가 매칭되면 세션쿠키를 발급합니다.")
    @Test
    void issue_success() throws Exception {
        // given
        SessionRequest.Issuance request = Issuance.builder()
                .userId("user")
                .password("1234")
                .build();
        MockHttpServletRequestBuilder requestBuilder = post("/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        // when & then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(cookie().value("SESSION", "550e8400-e29b-41d4-a716-446655440000"))
                .andDo(print());
    }
}
