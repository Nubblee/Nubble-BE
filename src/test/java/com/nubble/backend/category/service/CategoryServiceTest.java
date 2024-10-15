package com.nubble.backend.category.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nubble.backend.category.domain.Category;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Test
    void findRootCategory_shouldReturnAllRootCategory() {
        // 루트 카테고리를 한다.
        Category root = Category.builder()
                .name("root")
                .build();
        categoryRepository.save(root);

        // 루트 카테고리의 자식 카테고리를 저장한다.
        Category child = Category.builder()
                .name("child")
                .parentCategory(root)
                .build();
        categoryRepository.save(child);

        // 루트 카테고리를 가져온다.
        List<CategoryInfo> categoryInfos = categoryService.findRootCategory();

        // 루트 카테고리만 가져오므로 총 1개를 가져와야 한다.
        assertThat(categoryInfos).hasSize(1);
        assertThat(categoryInfos.getFirst().name()).isEqualTo(root.getName());
    }
}
