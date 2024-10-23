package com.nubble.backend.post.service;

import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.repository.PostRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    List<Post> findAllWithUserByBoardId(long boardId);
}
