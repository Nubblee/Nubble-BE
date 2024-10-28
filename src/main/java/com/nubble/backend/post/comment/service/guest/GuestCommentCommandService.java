package com.nubble.backend.post.comment.service.guest;

import com.nubble.backend.comment.domain.GuestComment;
import com.nubble.backend.comment.domain.GuestCommentRepository;
import com.nubble.backend.post.comment.service.CommentQuery.CommentByIdQuery;
import com.nubble.backend.post.comment.service.guest.GuestCommentCommand.DeleteCommand;
import com.nubble.backend.post.service.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GuestCommentCommandService {

    private final PostRepository postRepository;
    private final GuestCommentRepository guestCommentRepository;

    @Transactional
    public void delete(
            CommentByIdQuery commentQuery,
            DeleteCommand deleteCommand
    ) {
        GuestComment guestComment = guestCommentRepository.findById(commentQuery.id())
                .orElseThrow(() -> new RuntimeException("비회원 댓글이 존재하지 않습니다."));

        guestComment.validateAuthority(deleteCommand.guestPassword());
        guestCommentRepository.delete(guestComment);
    }
}
