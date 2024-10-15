package com.nubble.backend.post.service;

import com.nubble.backend.board.domain.Board;
import com.nubble.backend.board.service.BoardRepository;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.service.PostCommand.PostCreateCommand;
import com.nubble.backend.post.service.PostCommand.PostPublishCommand;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

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
                .build();

        return postRepository.save(newPost)
                .getId();
    }

    // todo publishPost -> updatePost로 변경, 모든 변수들을 변경할 수 있도록 수정, PostValidationHandler 객체를 만들어 검증 수행
    @Transactional
    public PostInfo publishPost(PostPublishCommand command) {
        Post post = postRepository.findById(command.postId())
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        post.validateOwner(command.userId());

        post.publish();
        post.updateThumbnailUrl(command.thumbnailUrl());
        post.updateDescription(command.description());

        return PostInfo.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .thumbnailUrl(post.getThumbnailUrl())
                .description(post.getDescription())
                .postStatus(post.getStatus().name())
                .build();
    }
}
