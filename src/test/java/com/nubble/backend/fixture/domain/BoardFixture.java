package com.nubble.backend.fixture.domain;

import com.nubble.backend.board.domain.Board;
import com.nubble.backend.category.domain.Category;

public class BoardFixture {
    private static final String DEFAULT_NAME = "스터디";

    private final Board.BoardBuilder builder;

    public static BoardFixture aBoard() {
        return new BoardFixture();
    }

    private  BoardFixture() {
        this.builder = Board.builder()
                .name(DEFAULT_NAME);
    }

    public Board build() {
        return this.builder.build();
    }

    public BoardFixture withCategory(Category category) {
        builder.category(category);
        return this;
    }
}
