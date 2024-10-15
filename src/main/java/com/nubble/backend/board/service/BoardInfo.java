package com.nubble.backend.board.service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardInfo {

    @Builder
    public record BoardDto(
            long boardId,
            String boardName,
            long categoryId
    ) {
    }
}
