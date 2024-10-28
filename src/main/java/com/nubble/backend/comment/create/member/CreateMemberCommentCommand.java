package com.nubble.backend.comment.create.member;

import lombok.Builder;

@Builder
public record CreateMemberCommentCommand(
        String content,
        long postId,
        long userId
) {

}
