package com.nubble.backend.post.comment.service.remover;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.nubble.backend.post.comment.domain.GuestComment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentDeleteCommand.GuestCommentDeleteCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GuestCommentAuthorizerTest {

    private final GuestCommentAuthorizer guestCommentAuthorizer = new GuestCommentAuthorizer();

    @DisplayName("게스트 댓글의 이름, 비밀번호 일치시 예외가 발생하지 않습니다.")
    @Test
    void authorize() {
        // given
        GuestComment comment = GuestComment.builder()
                .content("내용입니다.")
                .guestName("guest")
                .guestPassword("1234")
                .build();

        GuestCommentDeleteCommand command = GuestCommentDeleteCommand.builder()
                .guestName(comment.getGuestName())
                .guestPassword(comment.getGuestPassword())
                .build();

        // when & then
        assertThatCode(() ->
                guestCommentAuthorizer.authorize(comment, command))
                .doesNotThrowAnyException();
    }
}
