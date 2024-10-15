package com.nubble.backend.category.controller;

import com.nubble.backend.category.service.CategoryInfo;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CategoryResponseMapper {

    @Mapping(target = "categoryId", source = "id")
    @Mapping(target = "categoryName", source = "name")
    CategoryResponse.CategoryDto toCategoryFindResponse(CategoryInfo info);

    default CategoryResponse.CategoriesDto toCategoryFindResponses(List<CategoryInfo> infos) {
        List<CategoryResponse.CategoryDto> list = infos.stream()
                .map(this::toCategoryFindResponse)
                .toList();

        return CategoryResponse.CategoriesDto.builder()
                .categories(list)
                .build();
    }
}
