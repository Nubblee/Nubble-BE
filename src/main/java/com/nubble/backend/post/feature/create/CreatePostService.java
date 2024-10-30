package com.nubble.backend.post.feature.create;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.repository.PostRepository;
import com.nubble.backend.userold.domain.User;
import com.nubble.backend.userold.service.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreatePostService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final PostFactory postFactory;
    private final PostRepository postRepository;

    @Transactional
    public long create(CreatePostCommand command) {
        User user = userRepository.getUserById(command.userId());
        Board board = boardRepository.getBoardById(command.boardId());

        Post newPost = postFactory.generate(command, user, board);

        return postRepository.save(newPost)
                .getId();
    }

    @Builder
    public record CreatePostCommand(
            String title,
            String content,
            Long userId,
            Long boardId,
            PostStatus status,
            String thumbnailUrl,
            String description
    ) {

    }
}
