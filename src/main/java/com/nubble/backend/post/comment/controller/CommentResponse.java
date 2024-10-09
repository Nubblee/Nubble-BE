package com.nubble.backend.post.comment.controller;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResponse {

    @Builder
    public record CommentCreateResponse(
            Long commentId
    ) {
    }
}
