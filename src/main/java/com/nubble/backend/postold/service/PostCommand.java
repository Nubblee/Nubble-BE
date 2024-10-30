package com.nubble.backend.postold.service;

import com.nubble.backend.post.domain.PostStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCommand {

    @Builder
    public record PostUpdateCommand(
            long postId,
            String title,
            String content,
            long userId,
            long boardId,
            PostStatus status,
            String thumbnailUrl,
            String description
    ) {

    }
}
