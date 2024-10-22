package com.nubble.backend.category.board.service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardInfo {

    @Builder
    public record BoardDto(
            long id,
            String name,
            long categoryId
    ) {
    }
}
