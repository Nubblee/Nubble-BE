package com.nubble.backend.post.feature.unlinke;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
class UnlikePostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private SessionRepository sessionRepository;

    @MockBean
    private UnlikePostService unlikePostService;

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

    @DisplayName("게시글 좋아요를 취소한다")
    @Test
    void success() throws Exception {
        // http request
        long postId = 23L;
        MockHttpServletRequestBuilder requestBuilder = delete("/posts/{postId}/likes", postId)
                .header("SESSION-ID", session.getAccessId());

        // http response
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(content().string(""))
                .andDo(print());

    }
}
