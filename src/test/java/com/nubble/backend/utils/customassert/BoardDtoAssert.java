package com.nubble.backend.utils.customassert;

import com.nubble.backend.category.board.service.BoardInfo;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class BoardDtoAssert extends AbstractAssert<BoardDtoAssert, BoardInfo.BoardDto> {

    private BoardDtoAssert(BoardInfo.BoardDto actual) {
        super(actual, BoardDtoAssert.class);
        isNotNull();
    }

    public static BoardDtoAssert assertThat(BoardInfo.BoardDto actual) {
        return new BoardDtoAssert(actual);
    }

    public void isEqualTo(BoardInfo.BoardDto expected) {
        assertThat(expected).isNotNull();
        Assertions.assertThat(actual).usingRecursiveComparison()
                .withStrictTypeChecking().isEqualTo(expected);
    }
}
