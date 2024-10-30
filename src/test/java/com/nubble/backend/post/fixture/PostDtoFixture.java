package com.nubble.backend.post.fixture;

import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.feature.PostDto;
import java.time.LocalDateTime;

public class PostDtoFixture {

    private final PostDto.PostDtoBuilder builder;
    private Long postId;
    private String title = PostFixture.DEFAULT_TITLE;
    private String content = PostFixture.DEFAULT_CONTENT;
    private String thumbnailUrl = PostFixture.DEFAULT_THUMBNAIL_URL;
    private String description = PostFixture.DEFAULT_DESCRIPTION;
    private PostStatus status;
    private LocalDateTime createdAt;
    private Long userId;
    private Long boardId;

    public static PostDtoFixture aPostDto() {
        PostDtoFixture fixture = new PostDtoFixture();
        fixture.postId = 382L;
        fixture.status = PostStatus.PUBLISHED;
        fixture.createdAt = LocalDateTime.now();
        fixture.userId = 383L;
        fixture.boardId = 384L;

        return fixture;
    }

    private PostDtoFixture() {
        builder = PostDto.builder();
    }

    public PostDtoFixture postId(Long postId) {
        this.postId = postId;
        return this;
    }

    public PostDtoFixture userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public PostDtoFixture boardId(long boardId) {
        this.boardId = boardId;
        return this;
    }

    public PostDto build() {
        return builder.postId(postId)
                .title(title)
                .content(content)
                .thumbnailUrl(thumbnailUrl)
                .description(description)
                .status(status)
                .createdAt(createdAt)
                .userId(userId)
                .boardId(boardId).build();
    }
}
