package com.nubble.backend.post.feature.create;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.feature.create.CreatePostService.CreatePostCommand;
import com.nubble.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostFactory {

    private final PublishedPostGenerator publishedPostGenerator;
    private final DraftPostGenerator draftPostGenerator;

    public Post generate(CreatePostCommand command, User user, Board board) {
        PostGenerator generator = getGenerator(command.status());
        return generator.generate(command, user, board);
    }

    private PostGenerator getGenerator(PostStatus status) {
        return switch (status) {
            case PUBLISHED -> publishedPostGenerator;
            case DRAFT -> draftPostGenerator;
        };
    }
}
