package com.nubble.backend.comment.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.comment.controller.CommentRequest.GuestCommentCreateRequest;
import com.nubble.backend.comment.controller.CommentRequest.GuestCommentDeleteRequest;
import com.nubble.backend.comment.controller.CommentRequest.MemberCommentCreateRequest;
import com.nubble.backend.comment.controller.CommentResponse.CommentCreateResponse;
import com.nubble.backend.comment.controller.CommentResponse.CommentFindResponses;
import com.nubble.backend.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.comment.service.CommentInfo;
import com.nubble.backend.comment.service.CommentService;
import com.nubble.backend.comment.service.CommentType;
import com.nubble.backend.fixture.UserFixture;
import com.nubble.backend.session.domain.Session;
import com.nubble.backend.session.service.SessionRepository;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentResponseMapper commentResponseMapper;

    @DisplayName("멤버가 게시글에 댓글을 작성합니다.")
    @Test
    void createMemberComment_shouldCreateNewComment_whenAuthenticatedRequest() throws Exception {
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
        Long newCommentId = 123123L;
        given(commentService.createComment(command))
                .willReturn(newCommentId);

        CommentCreateResponse response = CommentCreateResponse.builder()
                .commentId(newCommentId)
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

    @DisplayName("게스트가 게시글에 댓글을 작성합니다.")
    @Test
    void createGuestComment_shouldCreateNewComment() throws Exception {
        // given
        Long postId = 123L;

        GuestCommentCreateRequest request = GuestCommentCreateRequest.builder()
                .guestName("게스트 이름")
                .guestPassword("1234")
                .content("댓글 내용입니다.")
                .build();
        String requestJson = objectMapper.writeValueAsString(request);

        Long newCommentId = 123123L;
        CommentCreateCommand command = commentCommandMapper.toCommentCreateCommand(request, postId);
        given(commentService.createComment(command))
                .willReturn(newCommentId);

        CommentCreateResponse response = CommentCreateResponse.builder()
                .commentId(newCommentId)
                .build();
        String responseJson = objectMapper.writeValueAsString(response);

        MockHttpServletRequestBuilder requestBuilder = post("/posts/{postId}/comments/guest", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson);

        // when & then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJson))
                .andDo(print());
    }

    @DisplayName("멤버가 자신이 작성한 댓글을 작성합니다.")
    @Test
    void deleteMemberComment_shouldDeleteMemberOwnComment() throws Exception {
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

    @DisplayName("게스트가 인증을 통해 게스트 댓글을 삭제합니다.")
    @Test
    void deleteGuestComment_shouldDeleteGuestComment_whenGuestIsAuthenticated() throws Exception {
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

    @DisplayName("게시글의 모든 댓글을 가져옵니다.")
    @Test
    void findAllCommentsByPostId_shouldFindAllCommentsByPostId() throws Exception {
        // given
        Long postId = 123L;
        List<CommentInfo> commentInfos = List.of(
                CommentInfo.builder()
                        .commentId(1L)
                        .content("댓글 내용1")
                        .createdAt(LocalDateTime.now())
                        .userId(1L)
                        .userName("사용자1")
                        .type(CommentType.MEMBER)
                        .build(),
                CommentInfo.builder()
                        .commentId(2L)
                        .content("댓글 내용2")
                        .createdAt(LocalDateTime.now())
                        .userId(2L)
                        .userName("사용자2")
                        .type(CommentType.GUEST)
                        .build()
        );

        given(commentService.findAllByPostId(postId))
                .willReturn(commentInfos);

        CommentFindResponses responses = commentResponseMapper.toCommentFindResponses(commentInfos);
        String responsesJson = objectMapper.writeValueAsString(responses);

        MockHttpServletRequestBuilder requestBuilder = get("/posts/{postId}/comments", postId);

        // when & then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responsesJson))
                .andDo(print());
    }
}
