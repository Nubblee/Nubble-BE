package com.nubble.backend.category.board.service;

import com.nubble.backend.category.board.domain.Board;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardInfoMapper boardInfoMapper;

    @Transactional(readOnly = true)
    public List<BoardInfo.BoardDto> findBoardByCategoryId(long categoryId) {
        List<Board> boards = boardRepository.findAllByCategoryId(categoryId);

        return boards.stream()
                .map(boardInfoMapper::toBoardDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public BoardInfo.BoardDto getBoardById(long boardId) {
        Board board = boardRepository.getBoardById(boardId);
        return boardInfoMapper.toBoardDto(board);
    }
}
