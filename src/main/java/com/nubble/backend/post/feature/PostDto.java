package com.nubble.backend.post.feature;

import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record PostDto(
        Long postId,
        String title,
        String content,
        String thumbnailUrl,
        String description,
        PostStatus status,
        LocalDateTime createdAt,
        Long userId,
        Long boardId,
        int likeCount
) {

    public static PostDto fromDomain(Post post) {
        return PostDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .thumbnailUrl(post.getThumbnailUrl())
                .description(post.getDescription())
                .status(post.getStatus())
                .createdAt(post.getCreatedAt())
                .userId(post.getUser().getId())
                .boardId(post.getBoard().getId())
                .likeCount(post.getLikeCount()).build();
    }
}
