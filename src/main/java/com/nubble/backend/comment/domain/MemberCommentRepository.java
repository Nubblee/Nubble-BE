package com.nubble.backend.comment.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCommentRepository extends JpaRepository<MemberComment, Long> {

}
