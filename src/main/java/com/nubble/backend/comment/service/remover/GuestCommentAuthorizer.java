package com.nubble.backend.comment.service.remover;

import com.nubble.backend.comment.domain.Comment;
import com.nubble.backend.comment.domain.GuestComment;
import com.nubble.backend.comment.service.CommentCommand.CommentDeleteCommand;
import com.nubble.backend.comment.service.CommentType;
import org.springframework.stereotype.Component;

@Component
public class GuestCommentAuthorizer implements CommentAuthorizer {

    @Override
    public boolean supports(Comment comment, CommentDeleteCommand command) {
        return comment instanceof GuestComment
                && command.type() == CommentType.GUEST;
    }

    @Override
    public void authorize(Comment comment, CommentDeleteCommand command) {
        if (!(comment instanceof GuestComment guestComment)) {
            throw new RuntimeException("잘못된 Comment 형식입니다. 필요한 Comment 형식: %s, 실행된 Comment 형식: %s"
                    .formatted(GuestComment.class.getSimpleName(), comment.getClass().getSimpleName()));
        }

        if (!matchNameAndPassWord(guestComment, command)) {
            throw new RuntimeException("게스트 이름 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    private boolean matchNameAndPassWord(GuestComment guestComment, CommentDeleteCommand command) {
        return guestComment.getGuestName().equals(command.guestName())
                && guestComment.getGuestPassword().equals(command.guestPassword());
    }
}
