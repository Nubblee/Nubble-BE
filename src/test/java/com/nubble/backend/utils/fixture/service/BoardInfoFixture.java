package com.nubble.backend.utils.fixture.service;

import com.nubble.backend.category.board.service.BoardInfo;
import com.nubble.backend.utils.fixture.domain.BoardFixture;

public class BoardInfoFixture {

    private BoardInfoFixture() {

    }

    public static BoardDtoFixture aBoardDto() {
        return new BoardDtoFixture();
    }

    public static class BoardDtoFixture {

        private final BoardInfo.BoardDto.BoardDtoBuilder builder;

        private BoardDtoFixture() {
            builder = BoardInfo.BoardDto.builder()
                    .id(2151L)
                    .categoryId(2152L)
                    .name(BoardFixture.DEFAULT_NAME);
        }

        public BoardInfo.BoardDto build() {
            return builder.build();
        }
    }
}
