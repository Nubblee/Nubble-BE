package com.nubble.backend.post.domain;

import com.nubble.backend.board.domain.Board;
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
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post {

    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MAX_CONTENT_LENGTH = 10_000;
    private static final int MAX_THUMBNAIL_URL_LENGTH = 255;
    private static final int MAX_DESCRIPTION_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false, length = MAX_TITLE_LENGTH)
    private String title;

    @Column(nullable = false, length = MAX_CONTENT_LENGTH)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(length = MAX_THUMBNAIL_URL_LENGTH)
    private String thumbnailUrl;

    @Column(length = MAX_DESCRIPTION_LENGTH)
    private String description;

    @Enumerated(value = EnumType.STRING)
    private PostStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Builder
    protected Post(
            String title,
            String content,
            User user,
            String thumbnailUrl,
            String description,
            PostStatus status,
            Board board) {
        this.user = user;
        validateUser();

        update(title, content, thumbnailUrl, description, status, board);
    }

    private void validateUser() {
        if (user == null) {
            throw new RuntimeException("유저는 필수입니다.");
        }
        if (user.getId() == null) {
            throw new RuntimeException("저장된 유저만 참조할 수 있습니다.");
        }
    }

    public void update(
            String title,
            String content,
            String thumbnailUrl,
            String description,
            PostStatus status,
            Board board) {
        updateTitle(title);
        updateContent(content);
        updateThumbnailUrl(thumbnailUrl);
        updateDescription(description);
        updateStatus(status);
        updateBoard(board);
    }

    private void updateTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new RuntimeException("제목은 비어있을 수 없습니다.");
        }
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new RuntimeException("제목은 최대 %d글자까지 가능합니다.".formatted(MAX_TITLE_LENGTH));
        }
        this.title = title;
    }

    private void updateContent(String content) {
        if (content == null || content.isEmpty()) {
            throw new RuntimeException("내용은 비어있을 수 없습니다.");
        }
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new RuntimeException("내용은 최대 %d글자까지 가능합니다.".formatted(MAX_CONTENT_LENGTH));
        }
        this.content = content;
    }

    // todo url만 오도록 수정, 업로드한 url 링크만 올 수 있도록 수정
    private void updateThumbnailUrl(String thumbnailUrl) {
        if (Objects.nonNull(thumbnailUrl) && thumbnailUrl.length() > MAX_THUMBNAIL_URL_LENGTH) {
            throw new RuntimeException("썸네일url은 최대 %d길이까지 가능합니다.".formatted(MAX_THUMBNAIL_URL_LENGTH));
        }
        this.thumbnailUrl = thumbnailUrl;
    }

    private void updateDescription(String description) {
        if (Objects.nonNull(description) && description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new RuntimeException("요약은 최대 %d글자까지 가능합니다.".formatted(MAX_DESCRIPTION_LENGTH));
        }
        this.description = description;
    }

    private void updateBoard(Board board) {
        if (board == null) {
            throw new RuntimeException("게시판은 필수입니다.");
        }
        if (board.getId() == null) {
            throw new RuntimeException("저장된 게시판만 참조할 수 있습니다.");
        }
        this.board = board;
    }

    private void updateStatus(PostStatus status) {
        if (status == PostStatus.PUBLISHED) {
            if (thumbnailUrl == null) {
                throw new RuntimeException("게시글을 게시하기 위해서는 썸네일이 필요합니다.");
            }
            if (description == null) {
                throw new RuntimeException("게시글을 게시하기 위해서는 요약이 필요합니다.");
            }
        }

        this.status = status;
    }

    public void validateOwner(Long userId) {
        if (!user.getId().equals(userId)) {
            throw new RuntimeException("게시글의 주인이 아닙니다.");
        }
    }
}
