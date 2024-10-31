package com.nubble.backend.post.repository;

import com.nubble.backend.post.domain.Post;
import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {

    List<Post> findAllWithUserByBoardId(long boardId);

    Optional<Post> findPostWithUserById(long postId);
}
