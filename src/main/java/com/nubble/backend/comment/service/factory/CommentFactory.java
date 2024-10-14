package com.nubble.backend.comment.service.factory;

import com.nubble.backend.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.comment.domain.Comment;
import com.nubble.backend.post.domain.Post;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentFactory {

    private final List<CommentGenerator<? extends Comment>> commentGenerators;

    public Comment generateComment(Post post, CommentCreateCommand command) {
        return commentGenerators.stream()
                .filter(creator -> creator.supports(command))
                .findFirst()
                .map(creator -> creator.generate(post, command))
                .orElseThrow(() -> new IllegalArgumentException("적합한 CommentCreator를 찾을 수 없습니다."));
    }
}
