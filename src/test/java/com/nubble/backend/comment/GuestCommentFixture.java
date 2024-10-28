package com.nubble.backend.comment;

import com.nubble.backend.comment.domain.GuestComment;
import com.nubble.backend.post.domain.Post;

public class GuestCommentFixture {

    private final GuestComment.GuestCommentBuilder builder;

    private String guestPassword = "1234";
    private String content = "댓글 내용";
    private String guestName = "guest";
    private Post post;

    public static GuestCommentFixture aGuestComment() {
        return new GuestCommentFixture();
    }

    private GuestCommentFixture() {
        builder = GuestComment.builder();
    }

    public GuestCommentFixture post(Post post) {
        this.post = post;
        return this;
    }

    public GuestComment build() {
        return builder
                .content(content)
                .guestName(guestName)
                .guestPassword(guestPassword)
                .post(post).build();
    }
}
