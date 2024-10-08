package com.nubble.backend.post.comment.service;


import com.nubble.backend.post.comment.domain.Comment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;

public interface CommentGenerator<T extends Comment> {

    boolean supports(CommentCreateCommand command);

    T generate(CommentCreateCommand command);
}
