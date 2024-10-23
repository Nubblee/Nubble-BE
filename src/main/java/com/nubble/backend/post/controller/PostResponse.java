package com.nubble.backend.post.controller;

import com.nubble.backend.post.comment.service.CommentTypeDto;
import com.nubble.backend.post.shared.PostStatusDto;
import java.time.LocalDateTime;
import java.util.List;
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

    @Builder
    public record CommentCreateResponse(
            long newCommentId
    ) {

    }

    @Builder
    public record CommentResponse(
            long commentId,
            String content,
            LocalDateTime createdAt,
            Long userId,
            String userName,
            String guestName,
            CommentTypeDto type
    ) {

    }

    @Builder
    public record CommentResponses(
            List<CommentResponse> comments
    ) {

    }

    @Builder
    public record PostDetailResponse(
            long postId,
            LocalDateTime createdAt,
            String title,
            String content,
            String thumbnailUrl,
            PostStatusDto postStatus,
            long userId,
            String userNickname
    ) {

    }
}
