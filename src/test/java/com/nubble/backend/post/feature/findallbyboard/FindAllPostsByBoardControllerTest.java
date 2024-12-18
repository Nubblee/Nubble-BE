package com.nubble.backend.post.feature.findallbyboard;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.post.feature.PostDto;
import com.nubble.backend.post.feature.PostWithUserDto;
import com.nubble.backend.post.feature.findallbyboard.FindAllPostsByBoardController.FindAllPostsByBoardResponse;
import com.nubble.backend.post.fixture.PostDtoFixture;
import com.nubble.backend.user.feature.UserDto;
import com.nubble.backend.user.feature.UserDtoFixture;
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

@SpringBootTest
@AutoConfigureMockMvc
class FindAllPostsByBoardControllerTest {

    @MockBean
    private FindAllPostsByBoardService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FindAllPostsByBoardMapper mapper;

    @DisplayName("게시판에 있는 게시된 글들을 가져온다")
    @Test
    void success() throws Exception {
        // http request
        long boardId = 2L;
        MockHttpServletRequestBuilder requestBuilder = get("/boards/{boardId}", boardId);

        // 게시판에 있는 글들을 조회
        UserDto user = UserDtoFixture.aUserDto().build();

        int postCount = 5;
        List<PostWithUserDto> posts = new ArrayList<>();
        for (long postId = 1; postId <= postCount; postId++) {
            PostDto postDto = PostDtoFixture.aPostDto()
                    .postId(postId)
                    .userId(user.id())
                    .boardId(3L)
                    .likeCount(2).build();
            posts.add(PostWithUserDto.builder()
                    .post(postDto)
                    .user(user).build());
        }

        given(service.findAllByBoardId(boardId))
                .willReturn(posts);

        // http response
        FindAllPostsByBoardResponse response = mapper.toResponse(posts);

        mockMvc.perform(requestBuilder)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }
}
