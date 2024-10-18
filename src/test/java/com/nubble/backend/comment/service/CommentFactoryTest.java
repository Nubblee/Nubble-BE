package com.nubble.backend.comment.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nubble.backend.board.domain.Board;
import com.nubble.backend.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.comment.domain.Comment;
import com.nubble.backend.comment.domain.GuestComment;
import com.nubble.backend.comment.domain.MemberComment;
import com.nubble.backend.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.comment.service.factory.CommentFactory;
import com.nubble.backend.fixture.domain.UserFixture;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.service.PostRepository;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CommentFactoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentFactory commentFactory;

    @Autowired
    private PostRepository postRepository;

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
    void generateMemberComment_success() {
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
                .content("댓글 내용입니다.")
                .userId(user.getId())
                .type(CommentType.MEMBER)
                .build();

        // when
        Comment comment = commentFactory.generateComment(post, command);

        // then
        assertThat(comment).isInstanceOf(MemberComment.class);
    }

    @DisplayName("비회원 댓글을 생성합니다.")
    @Test
    void test() {
        // given
        CommentCreateCommand command = CommentCreateCommand.builder()
                .content("댓글 내용입니다.")
                .guestName("guest")
                .guestPassword("1234")
                .type(CommentType.GUEST)
                .build();

        User user = UserFixture.aUser().build();
        userRepository.save(user);

        Post post = Post.builder()
                .user(user)
                .title("제목입니다.")
                .content("게시글 내용입니다.")
                .board(board)
                .build();
        postRepository.save(post);

        // when
        Comment comment = commentFactory.generateComment(post, command);

        // then
        assertThat(comment).isInstanceOf(GuestComment.class);
    }
}
