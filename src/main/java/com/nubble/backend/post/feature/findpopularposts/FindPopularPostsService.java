package com.nubble.backend.post.feature.findpopularposts;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
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
public class FindPopularPostsService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<PostWithUserDto> findPopularPosts(long boardId) {
        Board board = boardRepository.getBoardById(boardId);

        return postRepository.findAllByBoardOrderByLikeCountDesc(board).stream()
                .filter(post -> post.getLikeCount() > 0)
                .map(post -> {
                    PostDto postDto = PostDto.fromDomain(post);
                    UserDto userDto = UserDto.fromDomain(post.getUser());
                    return new PostWithUserDto(postDto, userDto);})
                .toList();
    }
}
