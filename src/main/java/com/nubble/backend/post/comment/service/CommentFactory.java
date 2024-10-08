package com.nubble.backend.post.comment.service;

import com.nubble.backend.post.comment.domain.Comment;
import com.nubble.backend.post.comment.domain.MemberComment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentFactory {

    private final UserRepository userRepository;

    public Comment genearteComment(CommentCreateCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));

        return MemberComment.builder()
                .content(command.content())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
