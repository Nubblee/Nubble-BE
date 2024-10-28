package com.nubble.backend.comment.create.guest;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateGuestCommentRequest(
        @NotBlank(message = "댓글 내용이 비어있습니다.")
        String content,

        @NotBlank(message = "이름은 비어있습니다.")
        String guestName,

        @NotBlank(message = "비밀번호가 비어있습니다.")
        String guestPassword
) {

}
