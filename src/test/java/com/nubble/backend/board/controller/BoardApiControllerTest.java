package com.nubble.backend.board.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.fixture.service.PostInfoFixture;
import com.nubble.backend.post.service.PostInfo;
import com.nubble.backend.post.service.PostService;
import java.util.ArrayList;
import java.util.List;
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
class BoardApiControllerTest {

    @MockBean
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardResponseMapper boardResponseMapper;

    @DisplayName("boarId와 매핑된 게시글들을 조회한다.")
    @Test
    void findByBoardId_shouldReturnPostsMappedToBoardId() throws Exception {
        // http request
        long boardId = 1L;
        MockHttpServletRequestBuilder requestBuilder = get("/boards/{boardId}", boardId);

        // boardId와 매핑된 게시글들
        int postCount = 5;
        List<PostInfo.PostDto> postsDto = new ArrayList<>();
        for (int postId = 1; postId <= postCount; postId++) {
            postsDto.add(PostInfoFixture.aPostDto()
                    .withId(postId)
                    .withUserId(2L)
                    .withBoardId(3L)
                    .build());
        }
        given(postService.findPostsByBoardId(boardId))
                .willReturn(postsDto);

        // http response
        BoardResponse.PostsDto postsDtoResponse = boardResponseMapper.toPostsDto(postsDto);

        // 엔드포인트 호출
        String responseJson = objectMapper.writeValueAsString(postsDtoResponse);
        mockMvc.perform(requestBuilder)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(responseJson))
                .andDo(print());
    }
}
