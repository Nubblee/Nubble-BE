package com.nubble.backend.post.comment.domain;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public abstract class Comment {

    private Long id;
    private String content;
    private LocalDateTime createdAt;

    protected Comment(String content, LocalDateTime createdAt) {
        this.content = content;
        this.createdAt = createdAt;
    }
}
