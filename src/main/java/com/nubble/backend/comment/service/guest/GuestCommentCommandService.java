package com.nubble.backend.comment.service.guest;

import com.nubble.backend.comment.domain.GuestComment;
import com.nubble.backend.comment.service.CommentQuery.CommentByIdQuery;
import com.nubble.backend.comment.service.CommentQuery.PostByIdQuery;
import com.nubble.backend.comment.service.guest.GuestCommentCommand.CreateCommand;
import com.nubble.backend.comment.service.guest.GuestCommentCommand.DeleteCommand;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.service.PostRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GuestCommentCommandService {

    private final PostRepository postRepository;
    private final GuestCommentRepository guestCommentRepository;

    @Transactional
    public long create(
            PostByIdQuery postQuery,
            CreateCommand createCommand) {
        Post post = postRepository.findById(postQuery.id())
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        GuestComment newGuestComment = GuestComment.builder()
                .createdAt(LocalDateTime.now())
                .guestName(createCommand.guestName())
                .guestPassword(createCommand.guestPassword()).build();

        newGuestComment.assignPost(post);
        return guestCommentRepository.save(newGuestComment)
                .getId();
    }

    @Transactional
    public void delete(
            CommentByIdQuery commentQuery,
            DeleteCommand deleteCommand
    ) {
        // 댓글을 가져온다.
        // 댓글 정보와 deleteCommand 정보가 일치하는지 비교한다.
        // 비교 결과 같다면, 댓글을 삭제한다.
    }
}
