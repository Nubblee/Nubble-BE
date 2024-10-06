package com.nubble.backend.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String thumbnail;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private PostStatus status;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
        this.status = PostStatus.DRAFT;
    }
}
