package com.nubble.backend.post.comment.service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentCommand {

    @Builder
    public record CommentCreateCommand(
            long postId,
            long userId,
            String content,
            String guestName,
            String guestPassword,
            CommentTypeDto type
    ) {

    }

    @Builder
    public record CommentDeleteCommand(
            long commentId,
            long userId,
            String guestName,
            String guestPassword,
            CommentTypeDto type
    ) {

    }
}
