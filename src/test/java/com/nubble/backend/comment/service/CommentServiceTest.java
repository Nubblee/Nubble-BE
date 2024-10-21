package com.nubble.backend.comment.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nubble.backend.board.domain.Board;
import com.nubble.backend.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.comment.domain.GuestComment;
import com.nubble.backend.comment.domain.MemberComment;
import com.nubble.backend.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.comment.service.CommentCommand.CommentDeleteCommand;
import com.nubble.backend.fixture.domain.UserFixture;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.service.PostRepository;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private CommentRepository commentRepository;

    private Board board;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void setup() {
        Category category = Category.builder()
                .name("루트 카테고리")
                .build();
        categoryRepository.save(category);

        board = Board.builder()
                .category(category)
                .name("게시판 이름")
                .build();
        boardRepository.save(board);
    }

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
                .board(board)
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
        assertThat(newCommentId).isNotNull();
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
                .board(board)
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

    @DisplayName("게시글의 모든 댓글을 가져옵니다.")
    @Test
    void findAllByPostId_shouldReturnAllCommentsForPost() {
        // given
        User user = UserFixture.aUser().build();
        userRepository.save(user);

        Post post = Post.builder()
                .user(user)
                .title("제목입니다.")
                .content("게시글 내용입니다.")
                .board(board)
                .build();
        postRepository.save(post);

        MemberComment memberComment = MemberComment.builder()
                .content("회원 댓글")
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
        commentRepository.save(memberComment);
        memberComment.assignPost(post);

        GuestComment guestComment = GuestComment.builder()
                .content("게스트 댓글")
                .createdAt(LocalDateTime.now().plusMinutes(1))
                .guestName("게스트")
                .guestPassword("password")
                .build();
        commentRepository.save(guestComment);
        guestComment.assignPost(post);

        // when
        List<CommentInfo> result = commentService.findAllByPostId(post.getId());

        // then
        assertThat(result).hasSize(2);

        assertThat(result.get(0)).satisfies(comment -> {
            assertThat(comment.content()).isEqualTo("게스트 댓글");
            assertThat(comment.type()).isEqualTo(CommentType.GUEST);
        });

        assertThat(result.get(1)).satisfies(comment -> {
            assertThat(comment.content()).isEqualTo("회원 댓글");
            assertThat(comment.type()).isEqualTo(CommentType.MEMBER);
        });
    }
}
