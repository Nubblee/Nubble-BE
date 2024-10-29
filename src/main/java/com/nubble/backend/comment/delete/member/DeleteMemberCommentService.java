package com.nubble.backend.comment.delete.member;

import com.nubble.backend.comment.domain.member.MemberComment;
import com.nubble.backend.comment.domain.member.MemberCommentRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteMemberCommentService {

    private final MemberCommentRepository memberCommentRepository;

    @Transactional
    public void delete(DeleteMemberCommentCommand command) {
        MemberComment memberComment = memberCommentRepository.getMemberCommentById(command.commentId);

        memberComment.validateAuthor(command.userId);
        memberCommentRepository.delete(memberComment);
    }

    @Builder
    public record DeleteMemberCommentCommand(
            long commentId,
            long userId
    ) {

    }
}
