package com.nubble.backend.postold.controller;

import com.nubble.backend.postold.shared.PostStatusDto;
import java.time.LocalDateTime;
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
    public record PostDetailResponse(
            Long postId,
            LocalDateTime createdAt,
            String title,
            String content,
            String thumbnailUrl,
            PostStatusDto postStatus,
            Long userId,
            String userNickname,
            Long boardId,
            String boardName,
            Long categoryId,
            String categoryName
    ) {

    }
}
