package com.nubble.backend.comment.service.guest;

import com.nubble.backend.comment.domain.GuestComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestCommentRepository extends JpaRepository<GuestComment, Long> {

}
