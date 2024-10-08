package com.nubble.backend.post.comment.service.factory;

import com.nubble.backend.post.comment.domain.GuestComment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.post.comment.service.CommentType;
import org.springframework.stereotype.Component;

@Component
public class GuestCommentGenerator implements CommentGenerator<GuestComment> {

    @Override
    public boolean supports(CommentCreateCommand command) {
        return command.type() == CommentType.GUEST;
    }

    @Override
    public GuestComment generate(CommentCreateCommand command) {
        return generateGuestComment(command);
    }

    private static GuestComment generateGuestComment(CommentCreateCommand guestCommand) {
        return GuestComment.builder()
                .content(guestCommand.content())
                .guestName(guestCommand.guestName())
                .guestPassword(guestCommand.guestPassword())
                .build();
    }
}
