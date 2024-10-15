package com.nubble.backend.board.service;

import com.nubble.backend.board.domain.Board;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByCategoryId(long categoryId);
}
