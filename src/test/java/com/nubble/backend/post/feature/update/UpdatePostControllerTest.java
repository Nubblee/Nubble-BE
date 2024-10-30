package com.nubble.backend.post.feature.update;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.feature.update.UpdatePostController.UpdatePostRequest;
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
class UpdatePostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UpdatePostService service;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private SessionRepository sessionRepository;

    private Session session;

    @BeforeEach
    void setUp() {
        User user = Mockito.spy(UserFixture.aUser().build());
        given(user.getId())
                .willReturn(771L);

        session = AuthSessionFixture.aAuthSession()
                .withUser(user).build();

        given(sessionRepository.findByAccessId(session.getAccessId()))
                .willReturn(Optional.ofNullable(session));
    }

    @DisplayName("게시글을 수정한다")
    @Test
    void success() throws Exception {
        // http request
        UpdatePostRequest request = UpdatePostRequest.builder()
                .title("수정할 제목")
                .content("수정할 내용")
                .boardId(1L)
                .status(PostStatus.PUBLISHED)
                .thumbnailUrl("https://example.com/thumbnail.jpg")
                .description("설명입니다.")
                .build();

        long postId = 123L;
        MockHttpServletRequestBuilder requestBuilder = put("/posts/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("SESSION-ID", session.getAccessId())
                .content(objectMapper.writeValueAsString(request));

        // http response
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(content().string(""))
                .andDo(print());
    }
}
