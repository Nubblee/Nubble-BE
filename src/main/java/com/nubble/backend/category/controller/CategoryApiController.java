package com.nubble.backend.category.controller;

import com.nubble.backend.category.board.service.BoardInfo;
import com.nubble.backend.category.board.service.BoardService;
import com.nubble.backend.category.service.CategoryInfo;
import com.nubble.backend.category.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;
    private final CategoryResponseMapper categoryResponseMapper;
    private final BoardService boardService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryResponse.CategoriesDto> findCategories() {
        List<CategoryInfo.CategoryDto> infos = categoryService.findRootCategory();

        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryResponseMapper.toCategoryFindResponses(infos));
    }

    @GetMapping(
            path = "/{categoryId}/boards",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CategoryResponse.BoardsDto> findBoardsByCategoryId(@PathVariable Long categoryId) {
        List<BoardInfo.BoardDto> infos = boardService.findBoardByCategoryId(categoryId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryResponseMapper.toBoardsDto(infos));
    }
}
