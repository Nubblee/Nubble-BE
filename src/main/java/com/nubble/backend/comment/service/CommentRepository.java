package com.nubble.backend.comment.service;

import com.nubble.backend.comment.repository.CommentRepositoryCustom;
import com.nubble.backend.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

}
