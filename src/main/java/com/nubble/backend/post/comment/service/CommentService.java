package com.nubble.backend.post.comment.service;

import com.nubble.backend.post.comment.domain.Comment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.post.comment.service.CommentCommand.CommentDeleteCommand;
import com.nubble.backend.post.comment.service.factory.CommentFactory;
import com.nubble.backend.post.comment.service.remover.CommentRemover;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentFactory commentFactory;
    private final CommentRepository commentRepository;
    private final CommentRemover commentRemover;

    @Transactional
    public long createComment(CommentCreateCommand command) {
        Comment newComment = commentFactory.generateComment(command);
        return commentRepository.save(newComment)
                .getId();
    }

    @Transactional
    public void deleteComment(CommentDeleteCommand command) {
        Comment comment = commentRepository.findById(command.getCommentId())
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));

        commentRemover.remove(comment, command);
    }
}
