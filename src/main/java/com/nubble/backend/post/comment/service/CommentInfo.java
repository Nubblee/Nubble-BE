package com.nubble.backend.post.comment.service;

import java.time.LocalDateTime;

public record CommentInfo(
        long commentId,
        String content,
        LocalDateTime createdAt,
        long userId,
        String userName,
        String guestName,
        CommentType type
) {

}
