package com.nubble.backend.category.service;

import com.nubble.backend.category.domain.Category;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryInfoMapper categoryInfoMapper;

    public List<CategoryInfo.CategoryDto> findRootCategory() {
        return categoryRepository.findAllByParentCategoryIsNull().stream()
                .map(categoryInfoMapper::toCategoryDto)
                .toList();
    }

    public CategoryInfo.CategoryDto getCategoryById(long categoryId) {
        Category category = categoryRepository.getCategoryById(categoryId);
        return categoryInfoMapper.toCategoryDto(category);
    }
}
