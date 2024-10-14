package com.nubble.backend.comment.service.remover;

import com.nubble.backend.comment.domain.Comment;
import com.nubble.backend.comment.domain.MemberComment;
import com.nubble.backend.comment.service.CommentCommand.CommentDeleteCommand;
import com.nubble.backend.comment.service.CommentType;
import org.springframework.stereotype.Component;

@Component
public class MemberCommentAuthorizer implements CommentAuthorizer {

    @Override
    public boolean supports(Comment comment, CommentDeleteCommand command) {
        return comment instanceof MemberComment
                && command.type() == CommentType.MEMBER;
    }

    @Override
    public void authorize(Comment comment, CommentDeleteCommand command) {
        if (!(comment instanceof MemberComment memberComment)) {
            throw new RuntimeException("잘못된 Comment 형식입니다. 필요한 Comment 형식: %s, 실행된 Comment 형식: %s"
                    .formatted(MemberComment.class.getSimpleName(), comment.getClass().getSimpleName()));
        }
        if (!matchUserId(memberComment, command)) {
            throw new IllegalStateException("사용자가 댓글을 삭제할 권한이 없습니다.");
        }
    }

    private static boolean matchUserId(MemberComment memberComment, CommentDeleteCommand command) {
        return memberComment.getUser().getId().equals(command.userId());
    }
}
