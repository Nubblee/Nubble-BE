package com.nubble.backend.post.comment.service;

import com.nubble.backend.post.comment.domain.Comment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentFactory commentFactory;
    private final CommentRepository commentRepository;

    @Transactional
    public long createComment(CommentCreateCommand command) {
        Comment newComment = commentFactory.generateComment(command);
        return commentRepository.save(newComment)
                .getId();
    }
}
