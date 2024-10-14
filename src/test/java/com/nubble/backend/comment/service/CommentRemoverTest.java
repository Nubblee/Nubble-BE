package com.nubble.backend.comment.service;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.nubble.backend.comment.domain.GuestComment;
import com.nubble.backend.comment.domain.MemberComment;
import com.nubble.backend.comment.service.CommentCommand.CommentDeleteCommand;
import com.nubble.backend.comment.service.remover.CommentRemover;
import com.nubble.backend.fixture.UserFixture;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CommentRemoverTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentRemover commentRemover;

    @DisplayName("멤버 댓글을 삭제합니다.")
    @Test
    void remove_shouldRemoveMemberComment_withoutExceptions() {
        // given
        User user = UserFixture.aUser().build();
        userRepository.save(user);

        MemberComment comment = MemberComment.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .content("내용입니다.")
                .build();
        commentRepository.save(comment);

        CommentDeleteCommand command = CommentDeleteCommand.builder()
                .userId(user.getId())
                .commentId(comment.getId())
                .type(CommentType.MEMBER)
                .build();

        // when & then
        assertThatCode(() ->
                commentRemover.remove(comment, command))
                .doesNotThrowAnyException();
    }

    @DisplayName("비회원 댓글을 삭제합니다.")
    @Test
    void remove_shouldRemoveGuestComment_withoutExceptions() {
        // given
        GuestComment comment = GuestComment.builder()
                .guestName("guest")
                .guestPassword("1234")
                .createdAt(LocalDateTime.now())
                .content("Guest comment content")
                .build();
        commentRepository.save(comment);

        CommentDeleteCommand command = CommentDeleteCommand.builder()
                .commentId(comment.getId())
                .guestName(comment.getGuestName())
                .guestPassword(comment.getGuestPassword())
                .type(CommentType.GUEST)
                .build();

        // when & then
        assertThatCode(() ->
                commentRemover.remove(comment, command))
                .doesNotThrowAnyException();
    }
}
