package com.nubble.backend.category.service;

import com.nubble.backend.category.domain.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByParentCategoryIsNull();

    default Category getCategoryById(long cateogryId) {
        return findById(cateogryId)
                .orElseThrow(() -> new RuntimeException("카테고리를 찾지 못했습니다."));
    }
}
