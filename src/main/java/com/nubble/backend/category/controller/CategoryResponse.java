package com.nubble.backend.category.controller;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryResponse {

    @Builder
    public record CategoryDto(
            long categoryId,
            String categoryName
    ) {

    }

    @Builder
    public record CategoriesDto(
            List<CategoryDto> categories
    ) {

    }

    @Builder
    public record BoardDto(
            long id,
            String name
    ) {
    }

    @Builder
    public record BoardsDto(
            List<BoardDto> boards
    ) {
    }
}
