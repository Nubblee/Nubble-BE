package com.nubble.backend.post.service;

import com.nubble.backend.board.domain.Board;
import com.nubble.backend.board.service.BoardRepository;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.service.PostCommand.PostCreateCommand;
import com.nubble.backend.post.service.PostCommand.PostUpdateCommand;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final PostInfoMapper postInfoMapper;

    @Transactional
    public long createPost(PostCreateCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));
        Board board = boardRepository.findById(command.boardId())
                .orElseThrow(() -> new RuntimeException("게시판이 존재하지 않습니다."));

        Post newPost = Post.builder()
                .title(command.title())
                .content(command.content())
                .user(user)
                .board(board)
                .status(PostStatus.valueOf(command.status().name()))
                .thumbnailUrl(command.thumbnailUrl())
                .description(command.description())
                .build();

        return postRepository.save(newPost)
                .getId();
    }

    @Transactional
    public void updatePost(PostUpdateCommand command) {
        Post post = postRepository.findById(command.postId())
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        Board board = boardRepository.findById(command.boardId())
                .orElseThrow(() -> new RuntimeException("게시판이 존재하지 않습니다."));
        post.validateOwner(command.userId());

        post.update(
                command.title(),
                command.content(),
                command.thumbnailUrl(),
                command.description(),
                PostStatus.valueOf(command.status().name()),
                board
        );
    }

    @Transactional(readOnly = true)
    public List<PostInfo.PostDto> findPostsByBoardId(long boardId) {
        return postRepository.findAllByBoardIdAndStatusNot(boardId, PostStatus.DRAFT).stream()
                .map(postInfoMapper::toPostDto)
                .toList();
    }


}
