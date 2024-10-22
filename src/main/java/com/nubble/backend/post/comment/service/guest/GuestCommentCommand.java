package com.nubble.backend.post.comment.service.guest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GuestCommentCommand {

    @Builder
    public record CreateCommand(
            String guestName,
            String guestPassword
    ) {

    }

    @Builder
    public record DeleteCommand(
            String guestName,
            String guestPassword
    ) {

    }
}
