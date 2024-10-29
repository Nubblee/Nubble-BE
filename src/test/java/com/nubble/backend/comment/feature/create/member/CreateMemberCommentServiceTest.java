package com.nubble.backend.comment.feature.create.member;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.comment.feature.create.member.CreateMemberCommentService.CreateMemberCommentCommand;
import com.nubble.backend.comment.repository.MemberCommentRepository;
import com.nubble.backend.postold.domain.Post;
import com.nubble.backend.postold.domain.PostStatus;
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
class CreateMemberCommentServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CreateMemberCommentService createMemberCommentService;

    @Autowired
    private MemberCommentRepository memberCommentRepository;

    private User user;
    private Board board;

    @BeforeEach
    void setUp() {
        user = UserFixture.aUser().build();
        userRepository.save(user);

        Category category = CategoryFixture.aCategory().build();
        categoryRepository.save(category);

        board = Board.builder()
                .category(category).build();
        boardRepository.save(board);
    }

    @DisplayName("유저가 댓글을 작성한다")
    @Test
    void success() {
        // 게시글 생성
        Post post = PostFixture.aPost()
                .user(user)
                .board(board)
                .status(PostStatus.PUBLISHED).build();
        postRepository.save(post);

        // 댓글 작성
        CreateMemberCommentCommand command = CreateMemberCommentCommand.builder()
                .content("댓글 남기기")
                .postId(post.getId())
                .userId(user.getId()).build();
        long newCommentId = createMemberCommentService.create(command);

        // 댓글 확인
        Assertions.assertThat(memberCommentRepository.findById(newCommentId)).isPresent();
    }

    @DisplayName("임시글에는 댓글을 작성할 수 없다")
    @Test
    void throwException() {
        // 임시글 생성
        Post post = PostFixture.aPost()
                .user(user)
                .board(board)
                .status(PostStatus.DRAFT).build();
        postRepository.save(post);

        // 댓글 작성 및 예외 발생
        CreateMemberCommentCommand command = CreateMemberCommentCommand.builder()
                .content("댓글 남기기")
                .postId(post.getId())
                .userId(user.getId()).build();
        Assertions.assertThatThrownBy(() -> createMemberCommentService.create(command))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("임시글에는 댓글을 달 수 없습니다.");
    }
}
