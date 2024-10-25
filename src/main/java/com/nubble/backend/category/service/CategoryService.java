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

    public CategoryInfo.CategoryDto getCategoryById(long cateogryId) {
        Category category = categoryRepository.getCategoryById(cateogryId);
        return categoryInfoMapper.toCategoryDto(category);
    }
}
