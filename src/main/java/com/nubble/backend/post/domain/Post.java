package com.nubble.backend.post.domain;

import com.nubble.backend.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    private String thumbnailUrl;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private PostStatus status;

    @Builder
    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        status = PostStatus.DRAFT;
    }

    public void publish() {
        if (status == PostStatus.PUBLISHED) {
            throw new RuntimeException("이미 게시된 상태입니다.");
        }
        status = PostStatus.PUBLISHED;
    }

    public void updateThumbnailUrl(String thumbnailUrl) {
        if (thumbnailUrl == null) {
            throw new RuntimeException("썸네일 값은 필수입니다.");
        }
        this.thumbnailUrl = thumbnailUrl;
    }

    public void updateDescription(String description) {
        if (description == null) {
            throw new RuntimeException("게시글 설명은 필수입니다.");
        }
        this.description = description;
    }

    public void validateOwner(Long userId) {
        if (!user.getId().equals(userId)) {
            throw new RuntimeException("게시글의 주인이 아닙니다.");
        }
    }
}
