package com.nubble.backend.post.comment.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Comment {

    private Long id;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public Comment(String content, LocalDateTime createdAt) {
        this.content = content;
        this.createdAt = createdAt;
    }
}
