package com.nubble.backend.post.comment.service;

import com.nubble.backend.post.comment.domain.Comment;
import com.nubble.backend.post.comment.domain.MemberComment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentDeleteCommand;
import com.nubble.backend.post.comment.service.CommentCommand.CommentDeleteCommand.MemberCommentDeleteCommand;
import org.springframework.stereotype.Component;

@Component
public class MemberCommentAuthorizer implements CommentAuthorizer {

    @Override
    public boolean supports(Comment comment, CommentDeleteCommand command) {
        return comment instanceof MemberComment && command instanceof MemberCommentDeleteCommand;
    }

    @Override
    public void authorize(Comment comment, CommentDeleteCommand command) {
        if (!(command instanceof MemberCommentDeleteCommand memberCommand)) {
            throw new RuntimeException("잘못된 Command 형식입니다. 필요한 Command 형식: %s, 실행된 Command 형식: %s"
                    .formatted(MemberCommentDeleteCommand.class.getSimpleName(), command.getClass().getSimpleName()));
        }
        if (!(comment instanceof MemberComment memberComment)) {
            throw new RuntimeException("잘못된 Comment 형식입니다. 필요한 Comment 형식: %s, 실행된 Comment 형식: %s"
                    .formatted(MemberComment.class.getSimpleName(), comment.getClass().getSimpleName()));
        }
        if (!matchUserId(memberCommand, memberComment)) {
            throw new IllegalStateException("사용자가 댓글을 삭제할 권한이 없습니다.");
        }
    }

    private static boolean matchUserId(MemberCommentDeleteCommand memberCommand, MemberComment memberComment) {
        return memberComment.getUser().getId().equals(memberCommand.getUserId());
    }
}
