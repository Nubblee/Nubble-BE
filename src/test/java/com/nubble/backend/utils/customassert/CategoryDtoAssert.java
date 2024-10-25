package com.nubble.backend.utils.customassert;

import com.nubble.backend.category.service.CategoryInfo;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class CategoryDtoAssert extends AbstractAssert<CategoryDtoAssert, CategoryInfo.CategoryDto> {

    private CategoryDtoAssert(CategoryInfo.CategoryDto actual) {
        super(actual, CategoryDtoAssert.class);
        isNotNull();
    }

    public static CategoryDtoAssert assertThat(CategoryInfo.CategoryDto actual) {
        return new CategoryDtoAssert(actual);
    }

    public void isEqualTo(CategoryInfo.CategoryDto expected) {
        assertThat(expected).isNotNull();
        Assertions.assertThat(actual).usingRecursiveComparison()
                .withStrictTypeChecking().isEqualTo(expected);
    }
}
