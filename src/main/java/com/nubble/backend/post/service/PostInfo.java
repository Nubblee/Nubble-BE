package com.nubble.backend.post.service;

import lombok.Builder;

@Builder
public record PostInfo(
        long postId,
        String title,
        String content,
        long userId,
        String thumbnailUrl,
        String description,
        String postStatus) {

}
