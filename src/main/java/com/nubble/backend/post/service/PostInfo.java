package com.nubble.backend.post.service;

import com.nubble.backend.post.shared.PostStatusDto;
import lombok.Builder;

@Builder
public record PostInfo(
        long postId,
        String title,
        String content,
        long userId,
        long boardId,
        String thumbnailUrl,
        String description,
        PostStatusDto postStatus) {

}
