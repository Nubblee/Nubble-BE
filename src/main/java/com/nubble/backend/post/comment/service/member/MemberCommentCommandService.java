package com.nubble.backend.post.comment.service.member;

import com.nubble.backend.post.comment.domain.MemberComment;
import com.nubble.backend.post.comment.service.CommentQuery.CommentByIdQuery;
import com.nubble.backend.post.comment.service.CommentQuery.PostByIdQuery;
import com.nubble.backend.post.comment.service.CommentQuery.UserByIdQuery;
import com.nubble.backend.post.comment.service.member.MemberCommentCommand.CreateCommand;
import com.nubble.backend.post.comment.service.member.MemberCommentCommand.DeleteCommand;
import com.nubble.backend.common.exception.NoAuthorizationException;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.service.PostRepository;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import java.time.LocalDateTime;
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
    public long createMemberComment(
            UserByIdQuery userQuery,
            PostByIdQuery postQuery,
            CreateCommand command) {
        User user = userRepository.findById(userQuery.id())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        Post post = postRepository.findById(postQuery.id())
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        MemberComment newMemberComment = MemberComment.builder()
                .content(command.content())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        newMemberComment.assignPost(post);
        return memberCommentRepository.save(newMemberComment)
                .getId();
    }

    @Transactional
    public void delete(
            CommentByIdQuery commentQuery,
            DeleteCommand command) {
        MemberComment memberComment = memberCommentRepository.findById(commentQuery.id())
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));

        if (!memberComment.isAuthor(command.userId())) {
            throw new NoAuthorizationException("댓글의 작성자가 아닙니다.");
        }
        memberCommentRepository.delete( memberComment);
    }
}
