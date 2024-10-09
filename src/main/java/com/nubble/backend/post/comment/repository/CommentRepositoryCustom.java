package com.nubble.backend.post.comment.repository;

import com.nubble.backend.post.comment.domain.Comment;
import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> findAllByPostId(Long postId);
}
