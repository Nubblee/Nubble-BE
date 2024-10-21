package com.nubble.backend.comment.service.guest;

import com.nubble.backend.board.domain.Board;
import com.nubble.backend.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.comment.domain.GuestComment;
import com.nubble.backend.comment.service.CommentQuery;
import com.nubble.backend.comment.service.CommentQuery.PostByIdQuery;
import com.nubble.backend.comment.service.guest.GuestCommentCommand.CreateCommand;
import com.nubble.backend.fixture.domain.BoardFixture;
import com.nubble.backend.fixture.domain.PostFixture;
import com.nubble.backend.fixture.domain.UserFixture;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.service.PostRepository;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class GuestCommentCommandServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    GuestCommentCommandService guestCommentCommandService;

    @Autowired
    GuestCommentRepository guestCommentRepository;

    private User user;

    private Post post;

    private Board board;

    @BeforeEach
    void initializeData() {
        // 댓글을 쓸 유저를 생성한다.
        user = UserFixture.aUser().build();
        userRepository.save(user);

        // 게시글을 생성한다.
        Category category = Category.builder()
                .name("루트 카테고리")
                .build();
        categoryRepository.save(category);

        board = BoardFixture.aBoard()
                .withCategory(category)
                .build();
        boardRepository.save(board);

        post = PostFixture.aPost()
                .withBoard(board)
                .withUser(user)
                .withStatus(PostStatus.PUBLISHED)
                .build();
        postRepository.save(post);
    }

    @Test
    void 게시된_게시글에_게스트댓글을_작성한다() {
        // 댓글을 작성한다.
        CommentQuery.PostByIdQuery postQuery = PostByIdQuery.builder()
                .id(post.getId()).build();
        GuestCommentCommand.CreateCommand command = CreateCommand.builder()
                .guestName("name")
                .guestPassword("password").build();

        long newGuestCommentId = guestCommentCommandService.create(postQuery, command);

        // 댓글이 작성되었는지 확인한다.
        Optional<GuestComment> result = guestCommentRepository.findById(newGuestCommentId);
        Assertions.assertThat(result).isPresent();
    }
}
