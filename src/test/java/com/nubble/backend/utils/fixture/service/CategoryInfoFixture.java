package com.nubble.backend.utils.fixture.service;

import com.nubble.backend.category.service.CategoryInfo;
import com.nubble.backend.utils.fixture.domain.CategoryFixture;

public class CategoryInfoFixture {
    private CategoryInfoFixture() {

    }

    public static CategoryDtoFixture aCategoryDto() {
        return new CategoryDtoFixture();
    }

    public static class CategoryDtoFixture {

        private final CategoryInfo.CategoryDto.CategoryDtoBuilder builder;

        private CategoryDtoFixture() {
            builder = CategoryInfo.CategoryDto.builder()
                    .id(3120L)
                    .name(CategoryFixture.DEFAULT_NAME);
        }

        public CategoryInfo.CategoryDto build() {
            return builder.build();
        }
    }
}
