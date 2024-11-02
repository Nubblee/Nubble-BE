package com.nubble.backend.post.feature.like;

import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostLike;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.repository.PostLikeRepository;
import com.nubble.backend.post.repository.PostRepository;
import com.nubble.backend.userold.domain.User;
import com.nubble.backend.userold.service.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikePostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public Long likePost(LikePostCommand command) {
        Post post = postRepository.getPostById(command.postId);
        User user = userRepository.getUserById(command.userId);
        if (post.getStatus() == PostStatus.DRAFT) {
            throw new IllegalStateException("임시 게시글에는 좋아요를 누를 수 없습니다.");
        }
        if (postLikeRepository.existsByPostAndUser(post, user)) {
            throw new IllegalStateException("이미 좋아요를 누른 게시글입니다.");
        }

        PostLike postLike = PostLike.builder()
                .post(post)
                .user(user).build();
        return postLikeRepository.save(postLike)
                .getId();
    }

    @Builder
    public record LikePostCommand(
            Long postId,
            Long userId
    ) {
    }
}
