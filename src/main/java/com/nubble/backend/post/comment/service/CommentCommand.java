package com.nubble.backend.post.comment.service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentCommand {

    @Builder
    public record CommentCreateCommand(
            String content,
            long userId
    ) {

    }
}
