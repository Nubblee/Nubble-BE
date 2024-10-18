package com.nubble.backend.fixture;

import com.nubble.backend.board.domain.Board;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.user.domain.User;

public class PostFixture {

    private static final String DEFAULT_TITLE = "제목입니다.";
    private static final String DEFAULT_CONTENT = "내용입니다.";
    private static final String DEFAULT_THUMBNAIL_URL = "썸네일의 URL입니다.";
    private static final String DEFAULT_DESCRIPTION = "요약입니다.";

    private final Post.PostBuilder builder;

    public static PostFixture aPost() {
        return new PostFixture();
    }

    private PostFixture() {
        this.builder = Post.builder()
                .title(DEFAULT_TITLE)
                .content(DEFAULT_CONTENT)
                .thumbnailUrl(DEFAULT_THUMBNAIL_URL)
                .description(DEFAULT_DESCRIPTION);
    }

    public Post build() {
        return this.builder.build();
    }

    public PostFixture withUser(User user) {
        builder.user(user);
        return this;
    }

    public PostFixture withBoard(Board board) {
        builder.board(board);
        return this;
    }
}
