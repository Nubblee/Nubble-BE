package com.nubble.backend.post.comment.service;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CommentInfo(
        long commentId,
        String content,
        LocalDateTime createdAt,
        Long userId,
        String userName,
        String guestName,
        CommentType type
) {


}
