package com.nubble.backend.category.board.controller;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardResponse {

    @Builder
    public record PostWithUserResponse(
            long id,
            String title,
            String thumbnailUrl,
            String description,
            String username,
            LocalDateTime createdAt
    ) {

    }

    @Builder
    public record PostsWithUserResponse(
            List<PostWithUserResponse> posts
    ) {

    }
}
