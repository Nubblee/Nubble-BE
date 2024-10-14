package com.nubble.backend.comment.service;

import com.nubble.backend.comment.domain.Comment;
import com.nubble.backend.comment.domain.GuestComment;
import com.nubble.backend.comment.domain.MemberComment;
import com.nubble.backend.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.comment.service.CommentCommand.CommentDeleteCommand;
import com.nubble.backend.comment.service.factory.CommentFactory;
import com.nubble.backend.comment.service.remover.CommentRemover;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.service.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentFactory commentFactory;
    private final CommentRepository commentRepository;
    private final CommentRemover commentRemover;

    @Transactional
    public long createComment(CommentCreateCommand command) {
        Post post = postRepository.findById(command.postId())
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        // todo 게시글이 댓글을 등록할 수 있는 상태일 때만 댓글을 등록합니다.
        Comment newComment = commentFactory.generateComment(post, command);
        return commentRepository.save(newComment)
                .getId();
    }

    @Transactional
    public void deleteComment(CommentDeleteCommand command) {
        Comment comment = commentRepository.findById(command.commentId())
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));

        commentRemover.remove(comment, command);
    }

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
