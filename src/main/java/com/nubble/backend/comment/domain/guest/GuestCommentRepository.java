package com.nubble.backend.comment.domain.guest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestCommentRepository extends JpaRepository<GuestComment, Long> {

    GuestComment getGuestCommentById(long commentId);
}
