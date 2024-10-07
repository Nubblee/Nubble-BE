package com.nubble.backend.post.controller;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostRequest {

    @Builder
    public record PostCreateRequest(
            String title,
            String content) {

    }

    @Builder
    public record PostPublishRequest(
            String thumbnailUrl,
            String description
    ) {

    }
}
