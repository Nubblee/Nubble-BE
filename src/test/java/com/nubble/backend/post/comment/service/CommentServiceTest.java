package com.nubble.backend.post.comment.service;

import com.nubble.backend.fixture.UserFixture;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.post.comment.service.CommentCommand.CommentDeleteCommand;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.service.PostRepository;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostRepository postRepository;

    @DisplayName("회원 댓글을 생성합니다.")
    @Test
    void createComment_validUser_success() {
        // given
        User user = UserFixture.aUser().build();
        userRepository.save(user);

        Post post = Post.builder()
                .user(user)
                .title("제목입니다.")
                .content("게시글 내용입니다.")
                .build();
        postRepository.save(post);

        CommentCreateCommand command = CommentCreateCommand.builder()
                .postId(post.getId())
                .content("댓글 내용입니다.")
                .userId(user.getId())
                .type(CommentType.MEMBER)
                .build();

        // when
        Long newCommentId = commentService.createComment(command);

        // then
        Assertions.assertThat(newCommentId).isNotNull();
    }

    @DisplayName("회원 댓글을 삭제합니다.")
    @Test
    void test() {
        // given
        User user = UserFixture.aUser().build();
        userRepository.save(user);

        Post post = Post.builder()
                .user(user)
                .title("제목입니다.")
                .content("게시글 내용입니다.")
                .build();
        postRepository.save(post);

        CommentCreateCommand commentCreateCommand = CommentCreateCommand.builder()
                .postId(post.getId())
                .content("댓글 내용입니다.")
                .userId(user.getId())
                .type(CommentType.MEMBER)
                .build();

        long commentId = commentService.createComment(commentCreateCommand);

        CommentDeleteCommand command = CommentDeleteCommand.builder()
                .userId(user.getId())
                .commentId(commentId)
                .type(CommentType.MEMBER)
                .build();

        // when & then
        Assertions.assertThatCode(() ->
                        commentService.deleteComment(command))
                .doesNotThrowAnyException();
    }
}
