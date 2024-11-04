package com.nubble.backend.post.repository;

import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostLike;
import com.nubble.backend.userold.domain.User;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPostAndUser(Post post, User user);
    boolean existsByPostIdAndUserId(Long postId, Long userId);

    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);

    default PostLike getPostLike(Long postId, Long userId) {
        return findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new EntityNotFoundException("좋아요가 존재하지 않습니다."));
    }
}
