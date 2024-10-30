package com.nubble.backend.comment.feature.delete.guest;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.comment.domain.guest.GuestComment;
import com.nubble.backend.comment.feature.delete.guest.DeleteGuestCommentService.DeleteGuestCommentCommand;
import com.nubble.backend.comment.fixture.GuestCommentFixture;
import com.nubble.backend.comment.repository.CommentRepository;
import com.nubble.backend.common.exception.NoAuthorizationException;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.postold.service.PostRepository;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import com.nubble.backend.utils.fixture.domain.CategoryFixture;
import com.nubble.backend.utils.fixture.domain.PostFixture;
import com.nubble.backend.utils.fixture.domain.UserFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DeleteGuestCommentServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private Post post;

    @Autowired
    private DeleteGuestCommentService deleteGuestCommentService;

    @BeforeEach
    void setUp() {
        // 게시글 생성
        Category category = CategoryFixture.aCategory().build();
        categoryRepository.save(category);

        Board board = Board.builder()
                .category(category).build();
        boardRepository.save(board);

        User user = UserFixture.aUser().build();
        userRepository.save(user);

        post = PostFixture.aPublishedPost()
                .user(user)
                .board(board).build();
        postRepository.save(post);
    }

    @DisplayName("게스트 댓글을 삭제한다")
    @Test
    void success() {
        // 댓글 생성
        GuestComment guestComment = GuestCommentFixture.aGuestComment()
                .post(post).build();
        commentRepository.save(guestComment);

        // 댓글 삭제
        DeleteGuestCommentCommand command = DeleteGuestCommentCommand.builder()
                .commentId(guestComment.getId())
                .guestPassword(guestComment.getGuestPassword()).build();
        deleteGuestCommentService.delete(command);

        // 댓글 삭제 확인
        Assertions.assertThat(commentRepository.findById(guestComment.getId())).isEmpty();
    }

    @DisplayName("게스트 비밀번호가 일치하지 않으면 삭제하지 않는다")
    @Test
    void throwException() {
        // 댓글 생성
        GuestComment guestComment = GuestCommentFixture.aGuestComment()
                .post(post).build();
        commentRepository.save(guestComment);

        // 댓글 삭제 및 예외 발생
        DeleteGuestCommentCommand command = DeleteGuestCommentCommand.builder()
                .commentId(guestComment.getId())
                .guestPassword(guestComment.getGuestPassword() + " ").build();

        Assertions.assertThatThrownBy(() -> deleteGuestCommentService.delete(command))
                .isInstanceOf(NoAuthorizationException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }
}
