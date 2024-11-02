package com.nubble.backend.post.feature.like;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.post.feature.like.LikePostService.LikePostCommand;
import com.nubble.backend.userold.domain.User;
import com.nubble.backend.userold.session.domain.Session;
import com.nubble.backend.userold.session.service.SessionRepository;
import com.nubble.backend.userold.session.service.SessionService;
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
class LikePostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private SessionRepository sessionRepository;

    @MockBean
    private LikePostService likePostService;

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

    @DisplayName("멤버가 게시글에 좋아요를 누른다")
    @Test
    void success() throws Exception {
        // http request
        long postId = 23L;
        MockHttpServletRequestBuilder requestBuilder = put("/posts/{postId}/likes", postId)
                .header("SESSION-ID", session.getAccessId());

        // 좋아요를 누른다
        LikePostCommand command = LikePostCommand.builder()
                .userId(user.getId())
                .postId(postId).build();

        long newLikeId = 102L;
        given(likePostService.likePost(command))
                .willReturn(newLikeId);

        // http response
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(String.format("""
                        {
                            "newLikeId": %d
                        }
                        """, newLikeId)))
                .andDo(print());
    }
}
