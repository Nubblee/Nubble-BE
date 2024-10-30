package com.nubble.backend.post.fixture;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.userold.domain.User;

public class PostFixture {

    public static final String DEFAULT_TITLE = "제목입니다.";
    public static final String DEFAULT_CONTENT = "내용입니다.";
    public static final String DEFAULT_THUMBNAIL_URL = "https://example.com/asdf.jpg";
    public static final String DEFAULT_DESCRIPTION = "요약입니다.";

    private String postType;
    private String title;
    private String content;
    private String thumbnailUrl;
    private String description;
    private Board board;
    private User user;

    public static PostFixture aPublishedPost() {
        PostFixture fixture = new PostFixture();
        fixture.postType = "published";
        fixture.title = DEFAULT_TITLE;
        fixture.content = DEFAULT_CONTENT;
        fixture.thumbnailUrl = DEFAULT_THUMBNAIL_URL;
        fixture.description = DEFAULT_DESCRIPTION;
        return fixture;
    }

    public static PostFixture aDraftPost() {
        PostFixture fixture = new PostFixture();
        fixture.postType = "draft";
        fixture.title = DEFAULT_TITLE;
        fixture.content = DEFAULT_CONTENT;
        return fixture;
    }

    private PostFixture() {
    }

    public PostFixture user(User user) {
        this.user = user;
        return this;
    }

    public PostFixture board(Board board) {
        this.board = board;
        return this;
    }

    public Post build() {
        if (postType.equals("published")) {
            return Post.publishedBuilder()
                    .title(title)
                    .content(content)
                    .thumbnailUrl(thumbnailUrl)
                    .description(description)
                    .user(user)
                    .board(board)
                    .build();
        } else if (postType.equals("draft")) {
            return Post.draftBuilder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .board(board)
                    .build();
        } else {
            throw new IllegalArgumentException("알 수 없는 게시글 타입입니다: " + postType);
        }
    }
}
