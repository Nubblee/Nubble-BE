package com.nubble.backend.comment.create.member;

import com.nubble.backend.comment.domain.CommentRepository;
import com.nubble.backend.comment.domain.MemberComment;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.service.PostRepository;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateMemberCommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public long create(CreateMemberCommentCommand command) {
        User user = userRepository.getUserById(command.userId());
        Post post = postRepository.getPostById(command.postId());

        MemberComment newMemberComment = MemberComment.builder()
                .content(command.content())
                .post(post)
                .user(user).build();
        return commentRepository.save(newMemberComment)
                .getId();
    }
}