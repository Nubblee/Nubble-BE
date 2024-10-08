package com.nubble.backend.post.comment.service;

import com.nubble.backend.post.comment.domain.MemberComment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand.MemberCommentCreateCommand;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberCommentGenerator implements CommentGenerator<MemberComment> {

    private final UserRepository userRepository;

    @Override
    public boolean supports(CommentCreateCommand command) {

        return command instanceof MemberCommentCreateCommand;
    }

    @Override
    public MemberComment generate(CommentCreateCommand command) {
        if (command instanceof MemberCommentCreateCommand memberCommand) {
            User user = userRepository.findById(memberCommand.getUserId())
                    .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

            return generateMemberComment(memberCommand, user);
        }

        throw new IllegalArgumentException(
                "잘못된 명령 유형입니다. 예상된 타입: %s, 실제 타입: %s"
                        .formatted(MemberCommentCreateCommand.class, command.getClass()));
    }

    private static MemberComment generateMemberComment(MemberCommentCreateCommand memberCommand, User user) {
        return MemberComment.builder()
                .content(memberCommand.getContent())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
    }
}
