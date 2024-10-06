package com.nubble.backend.post.controller;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResponse {

    @Builder
    public record PostCreateResponse(
            long postId
    ) {
    }
}
