package com.nubble.backend.comment.service.guest;

import com.nubble.backend.comment.domain.GuestComment;
import com.nubble.backend.comment.service.CommentQuery.PostByIdQuery;
import com.nubble.backend.comment.service.guest.GuestCommentCommand.CreateCommand;
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
}
