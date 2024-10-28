package com.nubble.backend.post.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.comment.domain.CommentInfo;
import com.nubble.backend.post.comment.mapper.CommentQueryMapper;
import com.nubble.backend.post.comment.mapper.MemberCommentCommandMapper;
import com.nubble.backend.post.comment.service.CommentService;
import com.nubble.backend.post.comment.service.CommentTypeDto;
import com.nubble.backend.post.comment.service.member.MemberCommentCommandService;
import com.nubble.backend.post.controller.PostRequest.PostCreateRequest;
import com.nubble.backend.post.controller.PostRequest.PostUpdateRequest;
import com.nubble.backend.post.controller.PostResponse.CommentsResponse;
import com.nubble.backend.post.controller.PostResponse.PostCreateResponse;
import com.nubble.backend.post.controller.PostResponse.PostDetailResponse;
import com.nubble.backend.post.mapper.PostCommandMapper;
import com.nubble.backend.post.mapper.PostResponseMapper;
import com.nubble.backend.post.service.PostCommand.PostCreateCommand;
import com.nubble.backend.post.service.PostFacade;
import com.nubble.backend.post.service.PostInfo.PostWithCategoryDto;
import com.nubble.backend.post.service.PostService;
import com.nubble.backend.post.shared.PostStatusDto;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import com.nubble.backend.user.session.domain.Session;
import com.nubble.backend.user.session.service.SessionRepository;
import com.nubble.backend.utils.fixture.domain.UserFixture;
import com.nubble.backend.utils.fixture.service.PostInfoFixture;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
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

    @MockBean
    private CommentService commentService;

    private User user;

    private Session session;

    @MockBean
    private PostFacade postFacade;

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

        // post를 생성한다
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

        long postId = 123L;
        MockHttpServletRequestBuilder requestBuilder = put("/posts/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("SESSION-ID", session.getAccessId())
                .content(requestJson);

        // http response
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    void 게시글의_모든_댓글을_가져온다() throws Exception {
        // http request
        long postId = 123L;
        MockHttpServletRequestBuilder requestBuilder = get("/posts/{postId}/comments", postId);

        // 게시글의 모든 댓글을 가져온다
        List<CommentInfo.CommentDto> commentInfos = List.of(
                CommentInfo.CommentDto.builder()
                        .commentId(1L)
                        .content("댓글 내용1")
                        .createdAt(LocalDateTime.now())
                        .userId(1L)
                        .userName("사용자1")
                        .type(CommentTypeDto.MEMBER)
                        .build(),
                CommentInfo.CommentDto.builder()
                        .commentId(2L)
                        .content("댓글 내용2")
                        .createdAt(LocalDateTime.now())
                        .userId(2L)
                        .userName("사용자2")
                        .type(CommentTypeDto.GUEST)
                        .build()
        );
        given(commentService.findAllByPostId(postId))
                .willReturn(commentInfos);

        // http response
        CommentsResponse responses = postResponseMapper.toCommentsResponse(commentInfos);
        String responsesJson = objectMapper.writeValueAsString(responses);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responsesJson))
                .andDo(print());
    }

    @DisplayName("게시글 내용을 가져온다")
    @Test
    void getPost_success() throws Exception {
        // http request
        long postId = 123L;
        MockHttpServletRequestBuilder requestBuilder = get("/posts/{postId}", postId);

        // 게시글 내용을 조회한다
        PostWithCategoryDto postWithCategory = PostInfoFixture.aPostWithCategoryDto().build();
        given(postFacade.getPostById(postId))
                .willReturn(postWithCategory);

        // http response
        PostDetailResponse response = postResponseMapper.toPostDetailResponse(postWithCategory);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }
}
