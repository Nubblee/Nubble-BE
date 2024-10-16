package com.nubble.backend.post.service;

import com.nubble.backend.post.shared.PostStatusDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCommand {

    @Builder
    public record PostCreateCommand(
            String title,
            String content,
            long userId,
            long boardId,
            PostStatusDto status,
            String thumbnailUrl,
            String description
    ) {

    }

    @Builder
    public record PostPublishCommand(
            long userId,
            long postId,
            String thumbnailUrl,
            String description
    ) {

    }
}
