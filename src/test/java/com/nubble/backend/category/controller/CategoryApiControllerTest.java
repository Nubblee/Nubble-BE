package com.nubble.backend.category.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.board.service.BoardInfo;
import com.nubble.backend.board.service.BoardService;
import com.nubble.backend.category.controller.CategoryResponse.CategoriesDto;
import com.nubble.backend.category.service.CategoryInfo;
import com.nubble.backend.category.service.CategoryService;
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
class CategoryApiControllerTest {

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private CategoryResponseMapper categoryResponseMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoardService boardService;

    @DisplayName("모든 루트 카테고리를 반환한다.")
    @Test
    void findCategories_success() throws Exception {
        // 저장되어 있는 루트 카테고리들의 정보
        CategoryInfo.CategoryDto rootCategory1 = CategoryInfo.CategoryDto.builder()
                .id(1L)
                .name("코딩테스트")
                .build();
        CategoryInfo.CategoryDto rootCategory2 = CategoryInfo.CategoryDto.builder()
                .id(1L)
                .name("스터디")
                .build();
        List<CategoryInfo.CategoryDto> infos = List.of(rootCategory1, rootCategory2);

        // 컨트롤러에 요청이 들어오면 서비스를 통해 저장된 루트 카테고리들을 가져온다.
        given(categoryService.findRootCategory())
                .willReturn(infos);

        /*
          HTTP Request:
          GET /categories HTTP/1.1
          Host: localhost:8080
         */
        MockHttpServletRequestBuilder requestBuilder = get("/categories");

        /*
           HTTP Response:
          HTTP/1.1 200 OK
          Content-Type: application/json

          {
            "categories": [
              {
                "id": 1,
                "name": "코딩테스트"
              },
              {
                "id": 2,
                "name": "스터디"
              }
            ]
          }
         */
        CategoriesDto responses = categoryResponseMapper.toCategoryFindResponses(infos);
        mockMvc.perform(requestBuilder)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(responses)))
                .andDo(print());
    }

    @DisplayName("카테고리와 매핑된 게시판들을 반환한다.")
    @Test
    void test() throws Exception {
        // 카테고리 정보
        long categoryId = 1L;

        // 카테고리와 매핑된 게시판 목록
        BoardInfo.BoardDto mappedBoard1 = BoardInfo.BoardDto.builder()
                .id(1L)
                .name("매핑된 게시판1")
                .build();
        BoardInfo.BoardDto mappedBoard2 = BoardInfo.BoardDto.builder()
                .id(2L)
                .name("매핑된 게시판2")
                .build();
        List<BoardInfo.BoardDto> infos = List.of(mappedBoard1, mappedBoard2);
        given(boardService.findBoardByCategoryId(categoryId))
                .willReturn(infos);

        /*
        HTTP Request:
        GET /categories/1 HTTP/1.1
        Host: localhost:8080
         */
        MockHttpServletRequestBuilder requestBuilder = get("/categories/{categoryId}/boards", categoryId);

        /*
        HTTP Response:
        HTTP/1.1 200 OK
        Content-Type: application/json

        {
            "boards": [
                {
                    "id": 1L,
                    "name": "매핑된 게시판1"
                },
                {
                    "id": 2L,
                    "name": "매핑된 게시판2"
                }
            ]
        }
         */
        CategoryResponse.BoardsDto response = categoryResponseMapper.toBoardsDto(infos);
        mockMvc.perform(requestBuilder)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }
}
