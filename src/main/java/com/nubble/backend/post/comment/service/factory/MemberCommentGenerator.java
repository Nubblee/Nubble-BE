package com.nubble.backend.post.comment.service.factory;

import com.nubble.backend.post.comment.domain.MemberComment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.post.comment.service.CommentType;
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
        return command.type() == CommentType.MEMBER;
    }

    @Override
    public MemberComment generate(CommentCreateCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        return generateMemberComment(command, user);
    }

    private static MemberComment generateMemberComment(CommentCreateCommand memberCommand, User user) {
        return MemberComment.builder()
                .content(memberCommand.content())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
    }
}
