package com.nubble.backend.comment.feature.delete.member;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
class DeleteMemberCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private SessionRepository sessionRepository;

    @MockBean
    private DeleteMemberCommentService deleteMemberCommentService;

    private Session session;

    @BeforeEach
    void setUp() {
        User userSpy = Mockito.spy(UserFixture.aUser().build());
        given(userSpy.getId())
                .willReturn(771L);

        session = AuthSessionFixture.aAuthSession()
                .withUser(userSpy).build();

        given(sessionRepository.findByAccessId(session.getAccessId()))
                .willReturn(Optional.ofNullable(session));
    }

    @DisplayName("멤버 댓글을 삭제한다")
    @Test
    void success() throws Exception {
        // http request
        Long postId = 123L;
        Long commentId = 123123L;
        MockHttpServletRequestBuilder requestBuilder = delete("/comments/member/{commentId}", postId, commentId)
                .header("SESSION-ID", session.getAccessId());

        // http response
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(content().string(""))
                .andDo(print());
    }
}
