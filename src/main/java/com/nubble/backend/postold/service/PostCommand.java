package com.nubble.backend.postold.service;

import com.nubble.backend.postold.shared.PostStatusDto;
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
    public record PostUpdateCommand(
            long postId,
            String title,
            String content,
            long userId,
            long boardId,
            PostStatusDto status,
            String thumbnailUrl,
            String description
    ) {

    }
}
