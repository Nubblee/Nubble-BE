package com.nubble.backend.utils.fixture.domain;

import com.nubble.backend.category.domain.Category;

public class CategoryFixture {

    public static final String DEFAULT_NAME = "스터디";

    private final Category.CategoryBuilder builder;

    public static CategoryFixture aCategory() {
        return new CategoryFixture();
    }

    private CategoryFixture() {
        builder = Category.builder()
                .name(DEFAULT_NAME);
    }

    public Category build() {
        return builder.build();
    }
}
