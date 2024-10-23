package com.nubble.backend.category.board.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardInfo.BoardDto;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("카테고리 아이디와 매핑되는 게시판을 반환한다.")
    @Test
    void findBoardByCategoryId_shouldReturnBoardDtoMappedCategoryId() {
        // 카테고리를 생성한다.
        Category mappedCategory = Category.builder()
                .name("매핑된 카테고리")
                .build();
        Category unmappedCategory = Category.builder()
                .name("다른 카테고리")
                .build();
        categoryRepository.saveAll(List.of(mappedCategory, unmappedCategory));

        // 카테고리와 매핑된 게시판을 생성한다.
        // 다른 카테고리와 매핑되는 게시판을 생성한다.
        Board mappedBoard1 = Board.builder()
                .name("반환되는 게시판1")
                .category(mappedCategory)
                .build();
        Board mappedBoard2 = Board.builder()
                .name("반환되는 게시판2")
                .category(mappedCategory)
                .build();
        Board unmappedBoard = Board.builder()
                .name("반환되지 않는 게시판")
                .category(unmappedCategory)
                .build();
        boardRepository.saveAll(List.of(mappedBoard1, mappedBoard2, unmappedBoard));

        // 카테고리 아이디를 통해 게시판의 정보를 찾는다.
        List<BoardDto> result = boardService.findBoardByCategoryId(mappedCategory.getId());

        // 카테고리와 매핑된 게시판이 반환되었는지 검증한다.
        assertThat(result).hasSize(2)
                .allMatch(dto -> dto.name().startsWith("반환되는 게시판"));
    }
}
