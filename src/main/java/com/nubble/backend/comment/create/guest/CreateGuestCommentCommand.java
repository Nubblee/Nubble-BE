package com.nubble.backend.comment.create.guest;

import lombok.Builder;

@Builder
public record CreateGuestCommentCommand(
        long postId,
        String guestName,
        String guestPassword,
        String content
) {

}
