package com.nubble.backend.comment.service.factory;

import com.nubble.backend.comment.domain.MemberComment;
import com.nubble.backend.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.comment.service.CommentType;
import com.nubble.backend.post.domain.Post;
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
    public MemberComment generate(Post post, CommentCreateCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        return generateMemberComment(post, command, user);
    }

    private static MemberComment generateMemberComment(Post post, CommentCreateCommand memberCommand, User user) {
        return MemberComment.builder()
                .content(memberCommand.content())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
    }
}
