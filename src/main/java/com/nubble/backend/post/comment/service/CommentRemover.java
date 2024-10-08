package com.nubble.backend.post.comment.service;

import com.nubble.backend.post.comment.domain.Comment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentDeleteCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommentRemover {

    private final List<CommentAuthorizer> commentAuthorizers;
    private final CommentRepository commentRepository;

    @Transactional
    public void remove(Comment comment, CommentDeleteCommand command) {
        commentAuthorizers.stream()
                .filter(authorizer -> authorizer.supports(comment, command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "삭제 권한을 확인할 수 없습니다. Comment: %s, Command: %s"
                                .formatted(comment.getClass().getSimpleName(), command.getClass().getSimpleName())))
                .authorize(comment, command);

        commentRepository.delete(comment);
    }
}
