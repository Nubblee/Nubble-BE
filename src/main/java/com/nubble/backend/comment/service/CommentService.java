package com.nubble.backend.comment.service;

import com.nubble.backend.comment.domain.Comment;
import com.nubble.backend.comment.domain.GuestComment;
import com.nubble.backend.comment.domain.MemberComment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<CommentInfo> findAllByPostId(long postId) {
        return commentRepository.findAllByPostId(postId).stream()
                .map(this::mapCommentToCommentInfo)
                .toList();
    }

    // todo 임시 매퍼이므로 분리하여야 함
    private CommentInfo mapCommentToCommentInfo(Comment comment) {
        if (comment instanceof MemberComment memberComment) {
            return CommentInfo.builder()
                    .commentId(memberComment.getId())
                    .content(memberComment.getContent())
                    .createdAt(memberComment.getCreatedAt())
                    .userId(memberComment.getUser().getId())
                    .userName(memberComment.getUser().getNickname())
                    .type(CommentType.MEMBER)
                    .build();
        } else if (comment instanceof GuestComment guestComment) {
            return CommentInfo.builder()
                    .commentId(guestComment.getId())
                    .content(guestComment.getContent())
                    .createdAt(guestComment.getCreatedAt())
                    .guestName(guestComment.getGuestName())
                    .type(CommentType.GUEST)
                    .build();
        }
        throw new RuntimeException("CommentInfo로 매핑할 수 없습니다.");
    }
}
