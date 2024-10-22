package com.nubble.backend.comment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.comment.controller.CommentRequest.GuestCommentDeleteRequest;
import com.nubble.backend.fixture.domain.UserFixture;
import com.nubble.backend.session.domain.Session;
import com.nubble.backend.session.service.SessionRepository;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CommentApiControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 멤버가_자신이_작성한_댓글을_삭제한다() throws Exception {
        // given
        User user = UserFixture.aUser().build();
        userRepository.save(user);

        Session session = Session.builder()
                .user(user)
                .accessId(UUID.randomUUID().toString())
                .expireAt(LocalDateTime.now().plusDays(1))
                .build();
        sessionRepository.save(session);

        Long postId = 123L;
        Long commentId = 123123L;

        MockHttpServletRequestBuilder requestBuilder = delete("/posts/{postId}/comments/member/{commentId}", postId,
                commentId)
                .header("SESSION-ID", session.getAccessId());

        // when & then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    void 게스트가_인증을_통해_게스트댓글을_삭제합니다() throws Exception {
        // given
        GuestCommentDeleteRequest request = GuestCommentDeleteRequest.builder()
                .guestName("게스트 이름")
                .guestPassword("1234")
                .build();
        String requestJson = objectMapper.writeValueAsString(request);

        Long postId = 123L;
        Long commentId = 123123L;

        MockHttpServletRequestBuilder requestBuilder = delete("/posts/{postId}/comments/guest/{commentId}", postId,
                commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson);

        // when & then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(content().string(""))
                .andDo(print());
    }
}
