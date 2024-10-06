package com.nubble.backend.post.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {

    private Long id;
    private String title;
    private String content;
    private String thumbnail;
    private String description;

    private PostStatus status;

    @Builder
    public Post(String title, String content, String thumbnail, String description) {
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.description = description;
    }
}
