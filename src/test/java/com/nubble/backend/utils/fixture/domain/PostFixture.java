package com.nubble.backend.utils.fixture.domain;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.postold.domain.Post;
import com.nubble.backend.postold.domain.PostStatus;
import com.nubble.backend.user.domain.User;

public class PostFixture {

    public static final String DEFAULT_TITLE = "제목입니다.";
    public static final String DEFAULT_CONTENT = "내용입니다.";
    public static final String DEFAULT_THUMBNAIL_URL = "썸네일의 URL입니다.";
    public static final String DEFAULT_DESCRIPTION = "요약입니다.";
    public static final PostStatus DEFAULT_POST_STATUS = PostStatus.PUBLISHED;

    private final Post.PostBuilder builder;

    public static PostFixture aPost() {
        return new PostFixture();
    }

    private PostFixture() {
        this.builder = Post.builder()
                .title(DEFAULT_TITLE)
                .content(DEFAULT_CONTENT)
                .thumbnailUrl(DEFAULT_THUMBNAIL_URL)
                .description(DEFAULT_DESCRIPTION)
                .status(DEFAULT_POST_STATUS);
    }

    public Post build() {
        return this.builder.build();
    }

    public PostFixture user(User user) {
        builder.user(user);
        return this;
    }

    public PostFixture board(Board board) {
        builder.board(board);
        return this;
    }

    public PostFixture status(PostStatus status) {
        builder.status(status);
        return this;
    }
}
