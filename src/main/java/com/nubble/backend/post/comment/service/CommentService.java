package com.nubble.backend.post.comment.service;

import com.nubble.backend.post.comment.domain.Comment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentFactory commentFactory;
    private final CommentRepository commentRepository;

    public Long createComment(CommentCreateCommand command) {
        Comment newComment = commentFactory.generateComment(command);
        return commentRepository.save(newComment)
                .getId();
    }
}
