package com.nubble.backend.comment;

import com.nubble.backend.comment.domain.member.MemberComment;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.user.domain.User;

public class MemberCommentFixture {
    public static final String DEFAULT_CONTENT = "댓글 내용";

    private final MemberComment.MemberCommentBuilder builder;

    public static MemberCommentFixture aMemberComment() {
        return new MemberCommentFixture();
    }

    private MemberCommentFixture() {
        builder = MemberComment.builder()
                .content(DEFAULT_CONTENT);
    }

    public MemberComment build() {
        return builder.build();
    }

    public MemberCommentFixture user(User user) {
        builder.user(user);
        return this;
    }

    public MemberCommentFixture post(Post post) {
        builder.post(post);
        return this;
    }
}
