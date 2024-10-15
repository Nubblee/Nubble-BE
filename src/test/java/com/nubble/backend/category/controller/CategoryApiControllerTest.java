package com.nubble.backend.category.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @DisplayName("모든 루트 카테고리를 반환한다.")
    @Test
    void findCategories_success() throws Exception {
        // 저장되어 있는 루트 카테고리들
        CategoryInfo rootCategory1 = CategoryInfo.builder()
                .id(1L)
                .name("코딩테스트")
                .build();
        CategoryInfo rootCategory2 = CategoryInfo.builder()
                .id(1L)
                .name("스터디")
                .build();
        List<CategoryInfo> infos = List.of(rootCategory1, rootCategory2);

        // 컨트롤러에 요청이 들어오면 서비스를 통해 저장된 루트 카테고리들을 가져온다.
        given(categoryService.findRootCategory())
                .willReturn(infos);

        /*
          HTTP Request:
          GET /categories HTTP/1.1
          Host: localhost:8080
          Accept: application/json
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
}
