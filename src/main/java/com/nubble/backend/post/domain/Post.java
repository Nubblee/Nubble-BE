package com.nubble.backend.post.domain;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.common.BaseEntity;
import com.nubble.backend.common.exception.NoAuthorizationException;
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
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseEntity {

    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MAX_CONTENT_LENGTH = 10_000;
    private static final int MAX_THUMBNAIL_URL_LENGTH = 2_000;
    private static final int MAX_DESCRIPTION_LENGTH = 255;
    private static final Pattern IMAGE_PATTERN = Pattern.compile("(?i)^.*\\.(jpeg|jpg|png|gif|webp)$");

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(length = MAX_THUMBNAIL_URL_LENGTH)
    private String thumbnailUrl;

    @Column(length = MAX_DESCRIPTION_LENGTH)
    private String description;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status;


    @Builder(builderClassName = "PublishedBuilder", builderMethodName = "publishedBuilder")
    protected Post(
            String title,
            String content,
            User user,
            Board board,
            String thumbnailUrl,
            String description) {
        validateTitle(title);
        validateContent(content);
        validateUser(user);
        validateBoard(board);
        validateThumbnailUrl(thumbnailUrl);
        validateDescription(description);

        this.title = title;
        this.content = content;
        this.user = user;
        this.board = board;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.status = PostStatus.PUBLISHED;
    }

    @Builder(builderClassName = "DraftBuilder", builderMethodName = "draftBuilder")
    protected Post(
            String title,
            String content,
            User user,
            Board board
    ) {
        validateTitle(title);
        validateContent(content);
        validateUser(user);
        validateBoard(board);

        this.title = title;
        this.content = content;
        this.user = user;
        this.board = board;
        this.status = PostStatus.DRAFT;
    }

    public void updateTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public void updateContent(String content) {
        validateContent(content);
        this.content = content;
    }

    public void updateBoard(Board board) {
        validateBoard(board);
        this.board = board;
    }

    public void publish(String thumbnailUrl, String description) {
        validateThumbnailUrl(thumbnailUrl);
        validateDescription(description);

        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        status = PostStatus.PUBLISHED;
    }

    public void validateOwner(Long userId) {
        if (!user.getId().equals(userId)) {
            throw new NoAuthorizationException("게시글의 주인이 아닙니다.");
        }
    }

    public void writeComment() {
        Assert.state(status != PostStatus.DRAFT, "임시글에는 댓글을 달 수 없습니다.");
    }

    private static void validateTitle(String title) {
        Assert.hasText(title, "게시글 제목이 채워주세요.");
        Assert.isTrue(title.length() <= MAX_TITLE_LENGTH,
                "게시글 제목은 최대 %d까지 가능합니다.".formatted(MAX_TITLE_LENGTH));
    }

    private static void validateContent(String content) {
        Assert.hasText(content, "게시글 내용을 채워주세요.");
        Assert.isTrue(content.length() <= MAX_CONTENT_LENGTH,
                "게시글 제목은 최대 %d까지 가능합니다.".formatted(MAX_CONTENT_LENGTH));
    }

    private static void validateUser(User user) {
        Assert.notNull(user, "유저는 필수입니다.");
    }

    private static void validateBoard(Board board) {
        Assert.notNull(board, "게시판을 선택해주세요.");

    }

    private static void validateThumbnailUrl(String thumbnailUrl) {
        Assert.hasText(thumbnailUrl, "썸네일은 필수입니다.");
        Assert.isTrue(thumbnailUrl.startsWith("http"), "썸네일은 http로 시작해야합니다.");
        Assert.isTrue(IMAGE_PATTERN.matcher(thumbnailUrl).matches(),
                "jpeg, jpg, png, gif, webp 파일 형태만 올 수 있습니다.");
    }

    private static void validateDescription(String description) {
        Assert.hasText(description, "설명을 채워주세요.");
        Assert.isTrue(description.length() <= MAX_DESCRIPTION_LENGTH,
                "설명은 최대 %d까지 가능합니다.".formatted(MAX_DESCRIPTION_LENGTH));
    }
}
