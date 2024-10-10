package com.nubble.backend.post.comment.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.fixture.UserFixture;
import com.nubble.backend.post.comment.controller.CommentRequest.MemberCommentCreateRequest;
import com.nubble.backend.post.comment.controller.CommentResponse.CommentCreateResponse;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.post.comment.service.CommentService;
import com.nubble.backend.post.comment.service.CommentType;
import com.nubble.backend.session.domain.Session;
import com.nubble.backend.session.service.SessionRepository;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
class CommentApiControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private CommentCommandMapper commentCommandMapper;

    @MockBean
    private CommentService commentService;

    @Autowired
    private CommentApiController commentApiController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("멤버가 게시글에 댓글을 작성합니다.")
    @Test
    void createMemberComment_shouldCreateNewComment_whenAuthorizedMemberRequest() throws Exception {
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

        MemberCommentCreateRequest request = MemberCommentCreateRequest.builder()
                .content("댓글의 내용입니다.")
                .build();
        String requestJson = objectMapper.writeValueAsString(request);

        CommentCreateCommand command = commentCommandMapper.toCommentCreateCommand(request, postId, user.getId(),
                CommentType.MEMBER);
        Long newCommandId = 123123L;
        given(commentService.createComment(command))
                .willReturn(newCommandId);

        CommentCreateResponse response = CommentCreateResponse.builder()
                .commentId(newCommandId)
                .build();
        String responseJson = objectMapper.writeValueAsString(response);

        MockHttpServletRequestBuilder requestBuilder = post("/posts/{postId}/comments/member", postId)
                .header("SESSION-ID", session.getAccessId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson);

        // when & then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJson))
                .andDo(print());
    }
}
