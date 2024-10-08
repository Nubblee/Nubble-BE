package com.nubble.backend.post.comment.service;

import com.nubble.backend.fixture.UserFixture;
import com.nubble.backend.post.comment.domain.Comment;
import com.nubble.backend.post.comment.domain.MemberComment;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommentFactoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentFactory commentFactory;

    @DisplayName("회원 댓글을 생성합니다.")
    @Test
    void genearteMemberComment_success() {
        // given
        User user = UserFixture.aUser().build();
        userRepository.save(user);

        CommentCreateCommand command = CommentCreateCommand.builder()
                .content("댓글 내용입니다.")
                .userId(user.getId())
                .build();

        // when
        Comment comment = commentFactory.genearteComment(command);

        // then
        Assertions.assertThat(comment).isInstanceOf(MemberComment.class);
    }
}
