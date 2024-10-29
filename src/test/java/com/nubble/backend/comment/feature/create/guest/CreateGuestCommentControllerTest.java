package com.nubble.backend.comment.feature.create.guest;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.comment.feature.create.CreateCommentResponse;
import com.nubble.backend.comment.feature.create.guest.CreateGuestCommentController.CreateGuestCommentRequest;
import com.nubble.backend.comment.feature.create.guest.CreateGuestCommentService.CreateGuestCommentCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@AutoConfigureMockMvc
@SpringBootTest
class CreateGuestCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CreateGuestCommentMapper mapper;

    @MockBean
    private CreateGuestCommentService service;

    @DisplayName("게스트가 게시글에 댓글을 작성한다")
    @Test
    void success() throws Exception {
        // http request
        Long postId = 2375L;
        CreateGuestCommentRequest request = CreateGuestCommentRequest.builder()
                .guestName("게스트 이름")
                .guestPassword("1234")
                .content("댓글 내용입니다.").build();

        MockHttpServletRequestBuilder requestBuilder = post("/posts/{postId}/comments/guest", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        // 댓글 작성
        long newCommentId = 1124L;
        CreateGuestCommentCommand command = mapper.toCommand(postId, request);
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
