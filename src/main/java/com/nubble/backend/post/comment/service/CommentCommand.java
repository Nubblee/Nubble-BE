package com.nubble.backend.post.comment.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentCommand {

    @Builder
    public record CommentCreateCommand(
            long userId,
            String content,
            String guestName,
            String guestPassword,
            CommentType type
    ) {
    }

    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public abstract static class CommentDeleteCommand {

        private final long commentId;

        @Getter
        public static class MemberCommentDeleteCommand extends CommentDeleteCommand {
            private final long userId;

            @Builder
            public MemberCommentDeleteCommand(long commentId, long userId) {
                super(commentId);
                this.userId = userId;
            }
        }

        @Getter
        public static class GuestCommentDeleteCommand extends CommentDeleteCommand {
            private final String guestName;
            private final String guestPassword;

            @Builder
            public GuestCommentDeleteCommand(long commentId, String guestName, String guestPassword) {
                super(commentId);
                this.guestName = guestName;
                this.guestPassword = guestPassword;
            }
        }
    }
}
