package com.nubble.backend.post.comment.service;

import com.nubble.backend.post.comment.domain.Comment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentFactory {

    private final List<CommentGenerator<? extends Comment>> commentGenerators;

    public Comment generateComment(CommentCreateCommand command) {
        return commentGenerators.stream()
                .filter(creator -> creator.supports(command))
                .findFirst()
                .map(creator -> creator.generate(command))
                .orElseThrow(() -> new IllegalArgumentException("적합한 CommentCreator를 찾을 수 없습니다."));
    }
}
