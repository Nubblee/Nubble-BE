package com.nubble.backend.comment.fixture;

import com.nubble.backend.comment.domain.guest.GuestComment;
import com.nubble.backend.postold.domain.Post;

public class GuestCommentFixture {


    public static final String DEFAULT_CONTENT = "댓글 내용";
    private static final String DEFAULT_GUEST_NAME = "guest";
    public static final String DEFAULT_GUEST_PASSWORD = "1234";

    private final GuestComment.GuestCommentBuilder builder;

    public static GuestCommentFixture aGuestComment() {
        return new GuestCommentFixture();
    }

    private GuestCommentFixture() {
        builder = GuestComment.builder()
                .content(DEFAULT_CONTENT)
                .guestName(DEFAULT_GUEST_NAME)
                .guestPassword(DEFAULT_GUEST_PASSWORD);
    }

    public GuestCommentFixture post(Post post) {
        builder.post(post);
        return this;
    }

    public GuestComment build() {
        return builder.build();
    }
}
