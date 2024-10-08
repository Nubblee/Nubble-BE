package com.nubble.backend.post.comment.service.remover;

import com.nubble.backend.post.comment.domain.Comment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentDeleteCommand;

public interface CommentAuthorizer {
    boolean supports(Comment comment, CommentDeleteCommand command);
    void authorize(Comment comment, CommentDeleteCommand command);
}
