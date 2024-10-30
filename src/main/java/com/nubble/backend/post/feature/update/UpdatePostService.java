package com.nubble.backend.post.feature.update;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.repository.PostRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdatePostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void update(UpdatePostCommand command) {
        Post post = postRepository.getPostById(command.postId());
        Board board = boardRepository.getBoardById(command.boardId());
        post.validateOwner(command.userId());

        post.updateTitle(command.title());
        post.updateContent(command.content());
        post.updateBoard(board);
        if (command.status() == PostStatus.PUBLISHED) {
            post.publish(command.thumbnailUrl(), command.description());
        }
    }

    @Builder
    public record UpdatePostCommand(
            Long postId,
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
