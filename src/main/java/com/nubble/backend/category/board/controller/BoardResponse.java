package com.nubble.backend.category.board.controller;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardResponse {

    @Builder
    public record PostDto(
            long id,
            String title,
            String thumbnailUrl,
            String description
    ) {

    }

    public record PostsDto(
            List<PostDto> posts
    ) {

    }
}
