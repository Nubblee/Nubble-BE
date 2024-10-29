package com.nubble.backend.comment.delete.guest;

import com.nubble.backend.comment.domain.guest.GuestComment;
import com.nubble.backend.comment.repository.GuestCommentRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteGuestCommentService {

    private final GuestCommentRepository repository;

    @Transactional
    public void delete(DeleteGuestCommentCommand command) {
        GuestComment guestComment = repository.getGuestCommentById(command.commentId);

        guestComment.validatePassword(command.guestPassword);
        repository.delete(guestComment);
    }

    @Builder
    public record DeleteGuestCommentCommand(
            Long commentId,
            String guestPassword
    ) {

    }
}
