package com.nubble.backend.post.repository;

import com.nubble.backend.post.domain.Post;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    List<Post> findAllWithUserByBoardId(long boardId);

    default Post getPostById(long postId) {
        return findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));
    }
}
