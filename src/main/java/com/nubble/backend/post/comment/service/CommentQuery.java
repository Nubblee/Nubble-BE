package com.nubble.backend.post.comment.service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentQuery {

    @Builder
    public record UserByIdQuery(long id) {

    }

    @Builder
    public record PostByIdQuery(long id) {

    }

    @Builder
    public record CommentByIdQuery(long id) {
        
    }
}
