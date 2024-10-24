package com.nubble.backend.post.comment.service;

import com.nubble.backend.post.comment.repository.CommentRepositoryCustom;
import com.nubble.backend.post.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

}
