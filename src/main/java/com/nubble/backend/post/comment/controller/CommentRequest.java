package com.nubble.backend.post.comment.controller;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentRequest {

    @Builder
    public record GuestCommentDeleteRequest(
            String guestName,
            String guestPassword
    ) {
    }
}
