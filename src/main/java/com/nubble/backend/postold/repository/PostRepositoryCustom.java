package com.nubble.backend.postold.repository;

import com.nubble.backend.postold.domain.Post;
import java.util.List;

public interface PostRepositoryCustom {

    List<Post> findAllWithUserByBoardId(long boardId);

    Post getWithUserById(long postId);
}
