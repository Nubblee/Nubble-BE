package com.nubble.backend.comment.shared;

import com.nubble.backend.comment.domain.guest.GuestComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestCommentRepository extends JpaRepository<GuestComment, Long> {

    GuestComment getGuestCommentById(long commentId);
}
