package com.nubble.backend.post.comment.service.remover;

import com.nubble.backend.post.comment.domain.Comment;
import com.nubble.backend.post.comment.domain.GuestComment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentDeleteCommand;
import com.nubble.backend.post.comment.service.CommentCommand.CommentDeleteCommand.GuestCommentDeleteCommand;
import org.springframework.stereotype.Component;

@Component
public class GuestCommentAuthorizer implements CommentAuthorizer {

    @Override
    public boolean supports(Comment comment, CommentDeleteCommand command) {
        return comment instanceof GuestComment
                && command instanceof GuestCommentDeleteCommand;
    }

    @Override
    public void authorize(Comment comment, CommentDeleteCommand command) {
        if (!(command instanceof GuestCommentDeleteCommand guestCommand)) {
            throw new RuntimeException("잘못된 Command 형식입니다. 필요한 Command 형식: %s, 실행된 Command 형식: %s"
                    .formatted(GuestCommentDeleteCommand.class.getSimpleName(), command.getClass().getSimpleName()));
        }
        if (!(comment instanceof GuestComment guestComment)) {
            throw new RuntimeException("잘못된 Comment 형식입니다. 필요한 Comment 형식: %s, 실행된 Comment 형식: %s"
                    .formatted(GuestComment.class.getSimpleName(), comment.getClass().getSimpleName()));
        }

        if (!matchNameAndPassWord(guestComment, guestCommand)) {
            throw new RuntimeException("게스트 이름 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    private boolean matchNameAndPassWord(GuestComment guestComment, GuestCommentDeleteCommand guestCommand) {
        return guestComment.getGuestName().equals(guestCommand.getGuestName())
                && guestComment.getGuestPassword().equals(guestCommand.getGuestPassword());
    }
}
