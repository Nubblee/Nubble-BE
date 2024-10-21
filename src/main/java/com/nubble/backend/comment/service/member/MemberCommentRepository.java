package com.nubble.backend.comment.service.member;

import com.nubble.backend.comment.domain.MemberComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCommentRepository extends JpaRepository<MemberComment, Long> {

}
