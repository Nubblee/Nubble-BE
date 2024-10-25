package com.nubble.backend.category.service;

import com.nubble.backend.category.domain.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

// todo 변경이 없으므로 외부 데이터베이스가 아닌 내부 데이터베이스를 사용하도록 수정
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByParentCategoryIsNull();

    default Category getCategoryById(long cateogryId) {
        return findById(cateogryId)
                .orElseThrow(() -> new RuntimeException("카테고리를 찾지 못했습니다."));
    }
}
