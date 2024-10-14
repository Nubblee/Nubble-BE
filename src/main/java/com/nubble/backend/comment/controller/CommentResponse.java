package com.nubble.backend.comment.controller;

import java.time.LocalDateTime;
import java.util.List;
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

    @Builder
    public record CommentFindResponses(
            List<CommentFindResponse> comments
    ) {
    }

    @Builder
    public record CommentFindResponse(
            Long commentId,
            String content,
            LocalDateTime createdAt,
            Long userId,
            String userName,
            String guestName,
            String commentType
    ) {
    }
}
