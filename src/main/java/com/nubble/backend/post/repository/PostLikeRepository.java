package com.nubble.backend.post.repository;

import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostLike;
import com.nubble.backend.userold.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPostAndUser(Post post, User user);
}
