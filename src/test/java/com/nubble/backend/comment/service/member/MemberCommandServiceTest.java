package com.nubble.backend.comment.service.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.nubble.backend.board.domain.Board;
import com.nubble.backend.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.comment.service.CommentQuery.PostByIdQuery;
import com.nubble.backend.comment.service.CommentQuery.UserByIdQuery;
import com.nubble.backend.fixture.domain.BoardFixture;
import com.nubble.backend.fixture.domain.PostFixture;
import com.nubble.backend.fixture.domain.UserFixture;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.exception.DraftPostException;
import com.nubble.backend.post.service.MemberCommentRepository;
import com.nubble.backend.post.service.PostRepository;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberCommandServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberCommandService memberCommandService;

    private User user;
    private Post post;
    private Board board;
    @Autowired
    private MemberCommentRepository memberCommentRepository;

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
    void 게시된_게시글에_댓글을_작성한다() {
        UserByIdQuery userQuery = UserByIdQuery.builder()
                .id(user.getId()).build();
        PostByIdQuery postQuery = PostByIdQuery.builder()
                .id(post.getId()).build();
        MemberCommentCommand.CreateCommand command = MemberCommentCommand.CreateCommand.builder()
                .comment("댓글 내용입니다.").build();

        // 댓글을 작성한다.
        long commentId = memberCommandService.create(userQuery, postQuery, command);

        // 댓글이 작성되었는지 확인한다.
        assertThat(memberCommentRepository.findById(commentId)).isPresent();
    }

    @Test
    void 게시되지_않은_게시글에_댓글이_작성할_수_없다() {
        // 게시되지 않은 게시글을 생성한다.
        Post unpulishedPost = PostFixture.aPost()
                .withBoard(board)
                .withUser(user)
                .withStatus(PostStatus.DRAFT)
                .build();
        postRepository.save(unpulishedPost);

        UserByIdQuery userQuery = UserByIdQuery.builder()
                .id(user.getId()).build();
        PostByIdQuery postQuery = PostByIdQuery.builder()
                .id(unpulishedPost.getId()).build();
        MemberCommentCommand.CreateCommand command = MemberCommentCommand.CreateCommand.builder()
                .comment("댓글 내용입니다.").build();

        // 댓글을 작성한다.
        // 예외를 발생시킨다.
        assertThatThrownBy(() -> memberCommandService.create(userQuery, postQuery, command))
                .isInstanceOf(DraftPostException.class);
    }
}
