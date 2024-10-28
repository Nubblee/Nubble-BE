package com.nubble.backend.comment.create.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateMemberCommentRequest(
        @NotBlank(message = "댓글 내용은 비어있을 수 없습니다.")
        String content
) {

}
