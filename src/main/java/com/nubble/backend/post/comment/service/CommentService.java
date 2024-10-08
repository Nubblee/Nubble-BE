package com.nubble.backend.post.comment.service;

import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentFactory commentFactory;

    public Long createComment(CommentCreateCommand command) {
        return commentFactory.genearteComment(command)
                .getId();
    }
}
