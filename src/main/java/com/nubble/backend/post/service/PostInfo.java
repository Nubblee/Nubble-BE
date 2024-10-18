package com.nubble.backend.post.service;

import com.nubble.backend.post.shared.PostStatusDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostInfo {

    @Builder
    public record PostDto(
            long id,
            String title,
            String content,
            String thumbnailUrl,
            String description,
            PostStatusDto status,
            long userId,
            long boardId) {
    }
}
