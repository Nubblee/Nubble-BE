package com.nubble.backend.user.controller;

import static com.nubble.backend.session.service.SessionCommand.SessionCreateCommand;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.nubble.backend.fixture.UserFixture;
import com.nubble.backend.session.service.SessionService;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionService sessionService;

    @DisplayName("인증 세션에 맞는 유저 정보를 반환합니다.")
    @Test
    void getLoggedInUser_success() throws Exception {
        // given
        User user = UserFixture.aUser()
                .build();
        userRepository.save(user);

        String sessionId = sessionService.createSession(
                SessionCreateCommand.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .build()).sessionId();

        MockHttpServletRequestBuilder requestBuilder = get("/users/me")
                .header("SESSION-ID", sessionId);

        // when & then
        mockMvc.perform(requestBuilder)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.nickname").value(user.getNickname()));
    }
}
