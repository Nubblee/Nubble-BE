package com.nubble.backend.board.domain;

import com.nubble.backend.category.domain.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Board {

    private Long id;

    private String name;

    private Category category;

    @Builder
    protected Board(String name, Category category) {
        this.name = name;
        this.category = category;
    }
}
