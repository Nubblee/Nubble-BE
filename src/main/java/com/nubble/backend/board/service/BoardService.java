package com.nubble.backend.board.service;

import com.nubble.backend.board.domain.Board;
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
}
