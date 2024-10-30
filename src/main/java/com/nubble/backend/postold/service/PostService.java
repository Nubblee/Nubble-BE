package com.nubble.backend.postold.service;

import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final PostInfoMapper postInfoMapper;

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
