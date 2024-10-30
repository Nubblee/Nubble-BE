package com.nubble.backend.post.feature.create;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.feature.create.CreatePostService.CreatePostCommand;
import com.nubble.backend.userold.domain.User;
import org.springframework.stereotype.Component;

@Component
public class DraftPostGenerator implements PostGenerator {

    @Override
    public Post generate(CreatePostCommand command, User user, Board board) {
        return Post.draftBuilder()
                .title(command.title())
                .content(command.content())
                .user(user)
                .board(board)
                .build();
    }
}
