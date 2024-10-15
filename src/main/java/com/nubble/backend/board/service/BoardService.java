package com.nubble.backend.board.service;

import com.nubble.backend.board.domain.Board;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    public final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<BoardInfo.BoardDto> findBoardByCategoryId(long categoryId) {
        List<Board> boards = boardRepository.findAllByCategoryId(categoryId);

        return boards.stream()
                .map(this::mapToBoardDto)
                .toList();
    }

    private BoardInfo.BoardDto mapToBoardDto(Board board) {
        return BoardInfo.BoardDto.builder()
                .boardId(board.getId())
                .boardName(board.getName())
                .categoryId(board.getCategory().getId())
                .build();
    }
}
