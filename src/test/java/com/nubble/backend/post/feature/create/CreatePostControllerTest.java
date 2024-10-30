package com.nubble.backend.post.feature.create;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.feature.create.CreatePostController.CreatePostRequest;
import com.nubble.backend.post.feature.create.CreatePostController.CreatePostResponse;
import com.nubble.backend.post.feature.create.CreatePostService.CreatePostCommand;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.session.domain.Session;
import com.nubble.backend.user.session.service.SessionRepository;
import com.nubble.backend.user.session.service.SessionService;
import com.nubble.backend.utils.fixture.domain.AuthSessionFixture;
import com.nubble.backend.utils.fixture.domain.UserFixture;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
class CreatePostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CreatePostMapper mapper;

    @MockBean
    private CreatePostService service;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private SessionRepository sessionRepository;

    private Session session;
    private User user;

    @BeforeEach
    void setUp() {
        user = Mockito.spy(UserFixture.aUser().build());
        given(user.getId())
                .willReturn(771L);

        session = AuthSessionFixture.aAuthSession()
                .withUser(user).build();

        given(sessionRepository.findByAccessId(session.getAccessId()))
                .willReturn(Optional.ofNullable(session));
    }

    @DisplayName("임시 게시글을 생성한다")
    @Test
    void success_createDraftPost() throws Exception {
        // http request
        CreatePostRequest request = CreatePostRequest.builder()
                .title("제목입니다.")
                .content("내용입니다")
                .boardId(1L)
                .status(PostStatus.DRAFT)
                .thumbnailUrl(null)
                .description(null).build();
        String requestJson = objectMapper.writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .header("SESSION-ID", session.getAccessId())
                .content(requestJson);

        // 게시글 생성
        long newPostId = 117L;
        CreatePostCommand command = mapper.toCommand(request, user.getId());
        given(service.create(command))
                .willReturn(newPostId);

        // http response
        CreatePostResponse response = new CreatePostResponse(newPostId);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }
}
