package com.nubble.backend.post.feature.create;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.feature.create.CreatePostService.CreatePostCommand;
import com.nubble.backend.userold.domain.User;
import org.springframework.stereotype.Component;

@Component
public class PublishedPostGenerator implements PostGenerator{

    @Override
    public Post generate(CreatePostCommand command, User user, Board board) {
        return Post.publishedBuilder()
                .title(command.title())
                .content(command.content())
                .user(user)
                .board(board)
                .thumbnailUrl(command.thumbnailUrl())
                .description(command.description())
                .build();
    }
}
