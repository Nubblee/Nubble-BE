package com.nubble.backend.post.comment.service.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCommentCommand {

    @Builder
    public record CreateCommand(

    ) {
    }

    @Builder
    public record DeleteCommand(
            long userId
    ) {
    }
}
