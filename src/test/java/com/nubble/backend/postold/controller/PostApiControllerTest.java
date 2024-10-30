package com.nubble.backend.postold.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.postold.controller.PostResponse.PostDetailResponse;
import com.nubble.backend.postold.mapper.PostCommandMapper;
import com.nubble.backend.postold.mapper.PostResponseMapper;
import com.nubble.backend.postold.service.PostFacade;
import com.nubble.backend.postold.service.PostInfo.PostWithCategoryDto;
import com.nubble.backend.postold.service.PostService;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import com.nubble.backend.user.session.domain.Session;
import com.nubble.backend.user.session.service.SessionRepository;
import com.nubble.backend.utils.fixture.domain.UserFixture;
import com.nubble.backend.utils.fixture.service.PostInfoFixture;
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
