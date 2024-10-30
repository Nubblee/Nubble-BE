package com.nubble.backend.post.feature.findallbyboard;

import com.nubble.backend.post.feature.PostDto;
import com.nubble.backend.post.feature.PostWithUserDto;
import com.nubble.backend.post.repository.PostRepository;
import com.nubble.backend.user.feature.UserDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindAllPostsByBoardService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<PostWithUserDto> findAllByBoardId(Long boardId) {
        return postRepository.findAllWithUserByBoardId(boardId).stream()
                .map(post -> PostWithUserDto.builder()
                        .post(PostDto.fromDomain(post))
                        .user(UserDto.fromDomain(post.getUser())).build())
                .toList();
    }
}
