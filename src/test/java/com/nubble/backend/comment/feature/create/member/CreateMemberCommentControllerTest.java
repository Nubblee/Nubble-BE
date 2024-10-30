package com.nubble.backend.comment.feature.create.member;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.comment.feature.create.CreateCommentResponse;
import com.nubble.backend.comment.feature.create.member.CreateMemberCommentController.CreateMemberCommentRequest;
import com.nubble.backend.comment.feature.create.member.CreateMemberCommentService.CreateMemberCommentCommand;
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

@AutoConfigureMockMvc
@SpringBootTest
class CreateMemberCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CreateMemberCommentMapper mapper;

    @MockBean
    private CreateMemberCommentService service;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private SessionRepository sessionRepository;

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

    @DisplayName("멤버가 게시글에 댓글을 작성한다")
    @Test
    void success() throws Exception {
        // http request
        Long postId = 2212L;
        CreateMemberCommentRequest request = CreateMemberCommentRequest.builder()
                .content("댓글 내용입니다.").build();

        MockHttpServletRequestBuilder requestBuilder = post("/posts/{postId}/comments/member", postId)
                .header("SESSION-ID", session.getAccessId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        // 댓글 작성
        CreateMemberCommentCommand command = mapper.toCommand(postId, session.getUser().getId(), request);
        long newCommentId = 1123L;
        given(service.create(command))
                .willReturn(newCommentId);

        // http response
        CreateCommentResponse response = new CreateCommentResponse(newCommentId);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }
}
