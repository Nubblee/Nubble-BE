package com.nubble.backend.comment.shared;

import com.nubble.backend.comment.domain.Comment;
import com.nubble.backend.comment.repository.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

}
