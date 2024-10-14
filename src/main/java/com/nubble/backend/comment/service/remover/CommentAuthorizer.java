package com.nubble.backend.comment.service.remover;

import com.nubble.backend.comment.domain.Comment;
import com.nubble.backend.comment.service.CommentCommand.CommentDeleteCommand;

public interface CommentAuthorizer {
    boolean supports(Comment comment, CommentDeleteCommand command);
    void authorize(Comment comment, CommentDeleteCommand command);
}
