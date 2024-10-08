package com.nubble.backend.post.comment.service;

import com.nubble.backend.post.comment.domain.GuestComment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand.GuestCommentCreateCommand;
import com.nubble.backend.user.service.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GuestCommentGenerator implements CommentGenerator<GuestComment> {

    private final UserRepository userRepository;

    @Override
    public boolean supports(CommentCreateCommand command) {
        return command instanceof GuestCommentCreateCommand;
    }

    @Override
    public GuestComment generate(CommentCreateCommand command) {
        if (command instanceof GuestCommentCreateCommand guestCommand) {
            return generateGuestComment(guestCommand);
        }

        throw new IllegalArgumentException(
                "잘못된 명령 유형입니다. 예상된 타입: %s, 실제 타입: %s"
                        .formatted(GuestCommentCreateCommand.class, command.getClass()));
    }

    private static GuestComment generateGuestComment(GuestCommentCreateCommand guestCommand) {
        return GuestComment.builder()
                .content(guestCommand.getContent())
                .guestName(guestCommand.getGuestName())
                .guestPassword(guestCommand.getGuestPassword())
                .build();
    }
}
