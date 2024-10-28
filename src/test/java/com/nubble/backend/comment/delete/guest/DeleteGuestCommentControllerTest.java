package com.nubble.backend.comment.delete.guest;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.comment.delete.guest.DeleteGuestCommentController.DeleteGuestCommentRequest;
import com.nubble.backend.comment.delete.guest.DeleteGuestCommentService.DeleteGuestCommentCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
class DeleteGuestCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeleteGuestCommentMapper mapper;

    @MockBean
    private DeleteGuestCommentService deleteGuestCommentService;

    @DisplayName("게스트 댓글을 삭제한다")
    @Test
    void success() throws Exception {
        // http request
        DeleteGuestCommentRequest request = new DeleteGuestCommentRequest("1234");
        long commentId = 369L;
        MockHttpServletRequestBuilder requestBuilder = delete("/comments/guest/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        // 게스트 댓글 삭제
        DeleteGuestCommentCommand command = mapper.toCommand(commentId, request);
        doNothing().when(deleteGuestCommentService).delete(command);

        // http response
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(content().string(""))
                .andDo(print());
    }
}
