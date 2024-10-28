package com.nubble.backend.post.comment.service.member;

import com.nubble.backend.comment.domain.MemberComment;
import com.nubble.backend.comment.domain.MemberCommentRepository;
import com.nubble.backend.post.comment.service.CommentQuery.CommentByIdQuery;
import com.nubble.backend.post.comment.service.member.MemberCommentCommand.DeleteCommand;
import com.nubble.backend.post.service.PostRepository;
import com.nubble.backend.user.service.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCommentCommandService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final MemberCommentRepository memberCommentRepository;

    @Transactional
    public void delete(
            CommentByIdQuery commentQuery,
            DeleteCommand command) {
        MemberComment memberComment = memberCommentRepository.findById(commentQuery.id())
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));

        memberComment.validateAuthor(String.valueOf(command.userId()));
        memberCommentRepository.delete( memberComment);
    }
}
