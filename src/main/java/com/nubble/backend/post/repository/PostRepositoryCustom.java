package com.nubble.backend.post.repository;

import com.nubble.backend.post.domain.Post;
import java.util.List;

public interface PostRepositoryCustom {

    List<Post> findAllWithUserByBoardId(long boardId);
}
