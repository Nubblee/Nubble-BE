package com.nubble.backend.post.controller;

import com.nubble.backend.post.shared.PostStatusDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostRequest {

    @Builder
    public record PostCreateRequest(
            String title,
            String content,
            Long boardId,
            PostStatusDto status,
            String thumbnailUrl,
            String description) {

    }

    @Builder
    public record PostPublishRequest(
            String thumbnailUrl,
            String description
    ) {

    }
}
