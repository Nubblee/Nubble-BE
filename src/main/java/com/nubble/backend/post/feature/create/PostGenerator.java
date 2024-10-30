package com.nubble.backend.post.feature.create;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.feature.create.CreatePostService.CreatePostCommand;
import com.nubble.backend.user.domain.User;

public interface PostGenerator {

    Post generate(CreatePostCommand command, User user, Board board);
}
