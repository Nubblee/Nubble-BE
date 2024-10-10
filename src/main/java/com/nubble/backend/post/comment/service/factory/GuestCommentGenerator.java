package com.nubble.backend.post.comment.service.factory;

import com.nubble.backend.post.comment.domain.GuestComment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.post.comment.service.CommentType;
import com.nubble.backend.post.domain.Post;
import org.springframework.stereotype.Component;

@Component
public class GuestCommentGenerator implements CommentGenerator<GuestComment> {

    @Override
    public boolean supports(CommentCreateCommand command) {
        return command.type() == CommentType.GUEST;
    }

    @Override
    public GuestComment generate(Post post, CommentCreateCommand command) {
        return generateGuestComment(post, command);
    }

    private static GuestComment generateGuestComment(Post post, CommentCreateCommand guestCommand) {
        return GuestComment.builder()
                .post(post)
                .content(guestCommand.content())
                .guestName(guestCommand.guestName())
                .guestPassword(guestCommand.guestPassword())
                .build();
    }
}
