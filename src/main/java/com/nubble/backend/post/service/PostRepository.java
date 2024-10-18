package com.nubble.backend.post.service;

import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByBoardIdAndStatusNot(long boardId, PostStatus status);
}
