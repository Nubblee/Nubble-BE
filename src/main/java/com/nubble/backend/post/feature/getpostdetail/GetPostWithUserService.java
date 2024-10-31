package com.nubble.backend.post.feature.getpostdetail;

import com.nubble.backend.common.exception.NoAuthorizationException;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.feature.PostDto;
import com.nubble.backend.post.feature.PostWithUserDto;
import com.nubble.backend.post.repository.PostRepository;
import com.nubble.backend.user.feature.UserDto;
import jakarta.persistence.EntityNotFoundException;
import java.util.Objects;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetPostWithUserService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public PostWithUserDto getPostWithUser(GetPostWithUserQuery query) {
        Post post = postRepository.findPostWithUserById(query.postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        if (post.getStatus() == PostStatus.DRAFT && !Objects.equals(post.getUser().getId(), query.userId)) {
            throw new NoAuthorizationException("글이 공개되지 않은 상태입니다.");
        }

        return PostWithUserDto.builder()
                .post(PostDto.fromDomain(post))
                .user(UserDto.fromDomain(post.getUser())).build();
    }

    @Builder
    public record GetPostWithUserQuery(
            Long postId,
            Long userId
    ) {

    }
}
