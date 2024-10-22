package com.nubble.backend.post.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.fixture.domain.UserFixture;
import com.nubble.backend.post.controller.PostRequest.PostCreateRequest;
import com.nubble.backend.post.controller.PostRequest.PostUpdateRequest;
import com.nubble.backend.post.controller.PostResponse.PostCreateResponse;
import com.nubble.backend.post.controller.mapper.PostCommandMapper;
import com.nubble.backend.post.controller.mapper.PostResponseMapper;
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

    @DisplayName("로그인된 유저가 임시 게시글을 작성합니다.")
    @Test
    void createPost_success() throws Exception {
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

    @DisplayName("작성자가 게시글을 수정한다.")
    @Test
    void updatePost_create() throws Exception {
        PostUpdateRequest request = PostUpdateRequest.builder()
                .title("수정할 제목")
                .content("수정할 내용")
                .boardId(1L)
                .status(PostStatusDto.PUBLISHED)
                .thumbnailUrl("https://example.com/thumbnail.jpg")
                .description("설명입니다.")
                .build();
        String requestJson = objectMapper.writeValueAsString(request);

        /*
        HTTP Request:
        PUT /posts/{postId}
        Content-Type: application/json
        SESSION-ID: {sessionAccessId}

        {
            "title": "수정할 제목",
            "content": "수정할 내용",
            "boardId": 1,
            "status": "PUBLISHED",
            "thumbnailUrl": "https://example.com/thumbnail.jpg",
            "description": "설명입니다."
        }
         */
        Long postId = 1L;
        MockHttpServletRequestBuilder requestBuilder = put("/posts/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("SESSION-ID", session.getAccessId())
                .content(requestJson);

        /*
        HTTP Response:
        HTTP/1.1 204 No Content
         */
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(content().string(""))
                .andDo(print());
    }
}
