package com.nubble.backend.post.comment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.post.comment.service.member.MemberCommentCommandService;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import com.nubble.backend.user.session.domain.Session;
import com.nubble.backend.user.session.service.SessionRepository;
import com.nubble.backend.utils.fixture.domain.UserFixture;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @MockBean
    private MemberCommentCommandService memberCommentCommandService;

    @Test
    void 멤버가_자신이_작성한_댓글을_삭제한다() throws Exception {
        // 유저를 생성하고, 인증 세션을 얻는다.
        User user = UserFixture.aUser().build();
        userRepository.save(user);

        Session session = Session.builder()
                .user(user)
                .accessId(UUID.randomUUID().toString())
                .expireAt(LocalDateTime.now().plusDays(1))
                .build();
        sessionRepository.save(session);

        // http request
        Long postId = 123L;
        Long commentId = 123123L;
        MockHttpServletRequestBuilder requestBuilder = delete("/comments/member/{commentId}", postId,
                commentId)
                .header("SESSION-ID", session.getAccessId());

        // http response
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(content().string(""))
                .andDo(print());
    }
}
