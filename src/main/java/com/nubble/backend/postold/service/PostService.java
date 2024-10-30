package com.nubble.backend.postold.service;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.postold.service.PostCommand.PostCreateCommand;
import com.nubble.backend.postold.service.PostCommand.PostUpdateCommand;
import com.nubble.backend.postold.shared.PostStatusDto;
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

        Post newPost = null;
        if (command.status() == PostStatusDto.PUBLISHED) {
            newPost = Post.publishedBuilder()
                    .title(command.title())
                    .content(command.content())
                    .user(user)
                    .board(board)
                    .thumbnailUrl(command.thumbnailUrl())
                    .description(command.description())
                    .build();
        } else if (command.status() == PostStatusDto.DRAFT) {
            newPost = Post.draftBuilder()
                    .title(command.title())
                    .content(command.content())
                    .user(user)
                    .board(board)
                    .build();
        } else {
            throw new IllegalArgumentException("해당 상태의 게시글을 생성할 수 없습니다.");
        }

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

        post.updateTitle(command.title());
        post.updateContent(command.content());
        post.updateBoard(board);
        if (command.status() == PostStatusDto.PUBLISHED) {
            post.publish(command.thumbnailUrl(), command.description());
        }
    }

    @Transactional(readOnly = true)
    public List<PostInfo.PostWithUserDto> findPostsByBoardId(long boardId) {
        return postRepository.findAllWithUserByBoardId(boardId).stream()
                .map(postInfoMapper::toPostWithUserDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public PostInfo.PostWithUserDto getPostById(long postId) {
        Post post = postRepository.getWithUserById(postId);
        return postInfoMapper.toPostWithUserDto(post);
    }
}
