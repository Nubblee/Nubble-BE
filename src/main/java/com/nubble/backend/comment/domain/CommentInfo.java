package com.nubble.backend.comment.domain;

import com.nubble.backend.post.comment.service.CommentTypeDto;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentInfo {

    @Builder
    public record CommentDto(
            long commentId,
            String content,
            LocalDateTime createdAt,
            Long userId,
            String userName,
            String guestName,
            CommentTypeDto type
    ) {

    }
}