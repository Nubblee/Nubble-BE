package com.nubble.backend.category.board.service;

import com.nubble.backend.category.board.domain.Board;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByCategoryId(long categoryId);

    default Board getBoardById(long boardId) {
        return findById(boardId)
                .orElseThrow(() -> new RuntimeException("보드를 찾지 못했습니다."));
    }
}
