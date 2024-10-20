package com.nubble.backend.comment.service.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCommentCommand {

    @Builder
    public record CreateCommand(
            String comment
    ) {
    }
}
