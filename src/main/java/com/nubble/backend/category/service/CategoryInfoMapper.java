package com.nubble.backend.category.service;

import com.nubble.backend.category.domain.Category;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CategoryInfoMapper {

    CategoryInfo.CategoryDto toCategoryInfo(Category c);
}
