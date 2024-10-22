package com.nubble.backend.post.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.comment.controller.CommentResponse.CommentCreateResponse;
import com.nubble.backend.comment.mapper.CommentQueryMapper;
import com.nubble.backend.comment.mapper.GuestCommentCommandMapper;
import com.nubble.backend.comment.mapper.MemberCommentCommandMapper;
import com.nubble.backend.comment.service.CommentQuery;
import com.nubble.backend.comment.service.guest.GuestCommentCommand;
import com.nubble.backend.comment.service.guest.GuestCommentCommandService;
import com.nubble.backend.comment.service.member.MemberCommentCommand;
import com.nubble.backend.comment.service.member.MemberCommentCommandService;
import com.nubble.backend.fixture.domain.UserFixture;
import com.nubble.backend.post.controller.PostRequest.PostCreateRequest;
import com.nubble.backend.post.controller.PostRequest.PostUpdateRequest;
import com.nubble.backend.post.controller.PostResponse.PostCreateResponse;
import com.nubble.backend.post.mapper.PostCommandMapper;
import com.nubble.backend.post.mapper.PostResponseMapper;
import com.nubble.backend.post.service.PostCommand.PostCreateCommand;
import com.nubble.backend.post.service.PostService;
import com.nubble.backend.post.shared.PostStatusDto;
import com.nubble.backend.session.domain.Session;
import com.nubble.backend.session.service.SessionRepository;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
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
class PostApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PostCommandMapper postCommandMapper;

    @Autowired
    private PostResponseMapper postResponseMapper;

    @MockBean
    private PostService postService;

    @Autowired
    private CommentQueryMapper commentQueryMapper;

    @Autowired
    private MemberCommentCommandMapper memberCommentCommandMapper;

    @MockBean
    private MemberCommentCommandService memberCommentCommandService;

    @Autowired
    private GuestCommentCommandMapper guestCommentCommandMapper;

    @MockBean
    private GuestCommentCommandService guestCommentCommandService;

    private User user;

    private Session session;

    @BeforeEach
    void initializeFixtures() {
        user = UserFixture.aUser().build();
        userRepository.save(user);

        session = Session.builder()
                .user(user)
                .accessId(UUID.randomUUID().toString())
                .expireAt(LocalDateTime.now().plusDays(1))
                .build();
        sessionRepository.save(session);
    }

    @Test
    void 로그인된_유저가_임시_게시글을_작성한다() throws Exception {
        // http request
        PostCreateRequest request = PostCreateRequest.builder()
                .title("제목입니다.")
                .content("내용입니다")
                .boardId(1L)
                .status(PostStatusDto.DRAFT)
                .thumbnailUrl(null)
                .description(null)
                .build();
        String requestJson = objectMapper.writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .header("SESSION-ID", session.getAccessId())
                .content(requestJson);

        // post를 생성한다.
        PostCreateCommand command = postCommandMapper.toPostCreateCommand(request, user.getId());
        long newPostId = 1L;
        given(postService.createPost(command))
                .willReturn(newPostId);

        // http response
        PostCreateResponse response = postResponseMapper.toPostCreateResponse(newPostId);
        String responseJson = objectMapper.writeValueAsString(response);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJson))
                .andDo(print());
    }

    @Test
    void 작성자가_게시글을_수정한다() throws Exception {
        // http request
        PostUpdateRequest request = PostUpdateRequest.builder()
                .title("수정할 제목")
                .content("수정할 내용")
                .boardId(1L)
                .status(PostStatusDto.PUBLISHED)
                .thumbnailUrl("https://example.com/thumbnail.jpg")
                .description("설명입니다.")
                .build();
        String requestJson = objectMapper.writeValueAsString(request);

        // http response
        Long postId = 1L;
        MockHttpServletRequestBuilder requestBuilder = put("/posts/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("SESSION-ID", session.getAccessId())
                .content(requestJson);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    void 멤버가_게시글에_댓글을_작성한다() throws Exception {
        // http request
        Long postId = 123L;
        PostRequest.MemberCommentCreateRequest request = PostRequest.MemberCommentCreateRequest.builder()
                .content("댓글의 내용입니다.")
                .build();
        String requestJson = objectMapper.writeValueAsString(request);

        // 댓글을 작성한다.
        CommentQuery.UserByIdQuery userQuery = commentQueryMapper.toUserByIdQuery(user.getId());
        CommentQuery.PostByIdQuery postQuery = commentQueryMapper.toPostByIdQuery(postId);
        MemberCommentCommand.CreateCommand command = memberCommentCommandMapper.toCreateCommand(request);
        long newCommentId = 123123L;
        given(memberCommentCommandService.createMemberComment(userQuery, postQuery, command))
                .willReturn(newCommentId);

        // http response
        MockHttpServletRequestBuilder requestBuilder = post("/posts/{postId}/comments/member", postId)
                .header("SESSION-ID", session.getAccessId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson);

        PostResponse.CommentCreateResponse response = PostResponse.CommentCreateResponse.builder()
                .newCommentId(newCommentId)
                .build();
        String responseJson = objectMapper.writeValueAsString(response);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJson))
                .andDo(print());
    }

    @Test
    void 게스트가_게시글에_댓글을_작성한다() throws Exception {
        // http request
        Long postId = 123L;
        PostRequest.GuestCommentCreateRequest request = PostRequest.GuestCommentCreateRequest.builder()
                .guestName("게스트 이름")
                .guestPassword("1234")
                .content("댓글 내용입니다.")
                .build();
        String requestJson = objectMapper.writeValueAsString(request);

        // 게스트가 댓글을 작성한다.
        Long newCommentId = 123123L;
        CommentQuery.PostByIdQuery postQuery = commentQueryMapper.toPostByIdQuery(postId);
        GuestCommentCommand.CreateCommand command = guestCommentCommandMapper.toCreateCommand(request);
        given(guestCommentCommandService.create(postQuery, command))
                .willReturn(newCommentId);

        // http response
        PostResponse.CommentCreateResponse response = PostResponse.CommentCreateResponse.builder()
                .newCommentId(newCommentId)
                .build();
        String responseJson = objectMapper.writeValueAsString(response);

        MockHttpServletRequestBuilder requestBuilder = post("/posts/{postId}/comments/guest", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJson))
                .andDo(print());
    }
}
