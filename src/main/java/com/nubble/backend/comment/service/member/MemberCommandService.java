package com.nubble.backend.comment.service.member;

import com.nubble.backend.comment.service.member.MemberCommentCommand.CreateCommand;
import com.nubble.backend.post.service.MemberCommentRepository;
import com.nubble.backend.post.service.PostQuery;
import com.nubble.backend.user.service.UserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberCommentRepository memberCommentRepository;

    @Transactional
    public long create(
            UserQuery.ByIdQuery userQuery,
            PostQuery.ByIdQuery postQuery,
            CreateCommand memberCommentcommand) {
        return 0;
    }
}
