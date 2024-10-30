package com.nubble.backend.post.feature.getpostdetail;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.category.board.service.BoardInfo.BoardDto;
import com.nubble.backend.category.service.CategoryInfo.CategoryDto;
import com.nubble.backend.post.feature.PostDto;
import com.nubble.backend.post.feature.getpostdetail.GetPostDetailController.GetPostDetailResponse;
import com.nubble.backend.post.feature.getpostdetail.GetPostDetailFacade.GetPostDetailFacadeResult;
import com.nubble.backend.post.feature.getpostdetail.GetPostWithUserService.GetPostWithUserQuery;
import com.nubble.backend.post.fixture.PostDtoFixture;
import com.nubble.backend.user.feature.UserDto;
import com.nubble.backend.user.feature.UserDtoFixture;
import com.nubble.backend.utils.fixture.service.BoardInfoFixture;
import com.nubble.backend.utils.fixture.service.CategoryInfoFixture;
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
class GetPostDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GetPostDetailFacade getPostDetailFacade;

    @Autowired
    private GetPostDetailMapper mapper;

    @DisplayName("게시글을 상세 조회한다")
    @Test
    void success() throws Exception {
        // http request
        long postId = 223L;
        MockHttpServletRequestBuilder requestBuilder = get("/posts/{postId}", postId);

        // 게시글 상세 조회
        UserDto user = UserDtoFixture.aUserDto().build();
        PostDto post = PostDtoFixture.aPostDto()
                .postId(postId)
                .userId(user.id()).build();
        BoardDto board = BoardInfoFixture.aBoardDto()
                .boardId(post.boardId()).build();
        CategoryDto category = CategoryInfoFixture.aCategoryDto()
                .categoryId(board.categoryId()).build();

        GetPostDetailFacadeResult result = GetPostDetailFacadeResult.builder()
                .post(post)
                .user(user)
                .board(board)
                .category(category).build();
        GetPostWithUserQuery query = GetPostWithUserQuery.builder()
                .postId(post.postId()).build();
        given(getPostDetailFacade.getPostDetailById(query))
                .willReturn(result);

        // http response
        GetPostDetailResponse response = mapper.toResponse(result);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }
}
