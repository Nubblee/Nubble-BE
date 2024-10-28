package com.nubble.backend.comment.create.guest;

import com.nubble.backend.comment.domain.CommentRepository;
import com.nubble.backend.comment.domain.GuestComment;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.service.PostRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateGuestCommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public long create(CreateGuestCommentCommand command) {
        Post post = postRepository.getPostById(command.postId());

        GuestComment newGuestComment = GuestComment.builder()
                .post(post)
                .guestName(command.guestName())
                .guestPassword(command.guestPassword())
                .content(command.content()).build();
        return commentRepository.save(newGuestComment)
                .getId();
    }

    @Builder
    public record CreateGuestCommentCommand(
            long postId,
            String guestName,
            String guestPassword,
            String content
    ) {

    }
}
