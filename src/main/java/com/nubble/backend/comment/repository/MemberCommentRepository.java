package com.nubble.backend.comment.repository;

import com.nubble.backend.comment.domain.member.MemberComment;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCommentRepository extends JpaRepository<MemberComment, Long> {

    default MemberComment getMemberCommentById(long commentId) {
        return findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));
    }
}
