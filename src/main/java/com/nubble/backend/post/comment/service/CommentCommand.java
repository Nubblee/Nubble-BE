package com.nubble.backend.post.comment.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentCommand {

    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public abstract static class CommentCreateCommand {
        private final String content;

        @Getter
        public static class MemberCommentCreateCommand extends CommentCreateCommand {
            private final long userId;

            @Builder
            public MemberCommentCreateCommand(String content, long userId) {
                super(content);
                this.userId = userId;
            }
        }

        @Getter
        public static class GuestCommentCreateCommand extends CommentCreateCommand {
            private final String guestName;
            private final String guestPassword;

            @Builder
            public GuestCommentCreateCommand(String content, String guestName, String guestPassword) {
                super(content);
                this.guestName = guestName;
                this.guestPassword = guestPassword;
            }
        }
    }
}
