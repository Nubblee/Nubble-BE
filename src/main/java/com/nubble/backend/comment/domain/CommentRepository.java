package com.nubble.backend.comment.domain;

import com.nubble.backend.post.comment.repository.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

}
