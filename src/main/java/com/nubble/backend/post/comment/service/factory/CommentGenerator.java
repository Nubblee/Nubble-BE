package com.nubble.backend.post.comment.service.factory;


import com.nubble.backend.post.comment.domain.Comment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.post.domain.Post;

public interface CommentGenerator<T extends Comment> {

    boolean supports(CommentCreateCommand command);

    T generate(Post post, CommentCreateCommand command);
}
