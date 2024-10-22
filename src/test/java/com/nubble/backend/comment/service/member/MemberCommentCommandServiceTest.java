package com.nubble.backend.comment.service.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.nubble.backend.board.domain.Board;
import com.nubble.backend.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.comment.domain.MemberComment;
import com.nubble.backend.comment.service.CommentQuery.CommentByIdQuery;
import com.nubble.backend.comment.service.CommentQuery.PostByIdQuery;
import com.nubble.backend.comment.service.CommentQuery.UserByIdQuery;
import com.nubble.backend.comment.service.member.MemberCommentCommand.DeleteCommand;
import com.nubble.backend.common.exception.NoAuthorizationException;
import com.nubble.backend.fixture.domain.BoardFixture;
import com.nubble.backend.fixture.domain.PostFixture;
import com.nubble.backend.fixture.domain.UserFixture;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.exception.DraftPostException;
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
class MemberCommentCommandServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberCommentCommandService memberCommentCommandService;

    @Autowired
    private MemberCommentRepository memberCommentRepository;

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
    void 게시된_게시글에_댓글을_작성한다() {
        // 댓글을 작성한다.
        UserByIdQuery userQuery = UserByIdQuery.builder()
                .id(user.getId()).build();
        PostByIdQuery postQuery = PostByIdQuery.builder()
                .id(post.getId()).build();
        MemberCommentCommand.CreateCommand command = MemberCommentCommand.CreateCommand.builder()
                .content("댓글 내용입니다.").build();

        long commentId = memberCommentCommandService.create(userQuery, postQuery, command);

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
                .content("댓글 내용입니다.").build();

        // 댓글을 작성한다.
        // 예외를 발생시킨다.
        assertThatThrownBy(() -> memberCommentCommandService.create(userQuery, postQuery, command))
                .isInstanceOf(DraftPostException.class);
    }

    @Test
    void 댓글의_작성자가_댓글을_삭제한다() {
        // 댓글을 작성한다.
        UserByIdQuery userQuery = UserByIdQuery.builder()
                .id(user.getId()).build();
        PostByIdQuery postQuery = PostByIdQuery.builder()
                .id(post.getId()).build();
        MemberCommentCommand.CreateCommand createCommand = MemberCommentCommand.CreateCommand.builder()
                .content("댓글 내용입니다.").build();

        long commentId = memberCommentCommandService.create(userQuery, postQuery, createCommand);

        // 댓글을 삭제한다.
        CommentByIdQuery commentQuery = CommentByIdQuery.builder()
                .id(commentId).build();
        DeleteCommand deleteCommand = DeleteCommand.builder()
                .userId(user.getId()).build();

        memberCommentCommandService.delete(commentQuery, deleteCommand);

        // 삭제되었는지 확인한다.
        Optional<MemberComment> result = memberCommentRepository.findById(commentId);
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    void 댓글의_작성자가_아니라면_댓글을_삭제할_수_없다() {
        // 댓글을 작성한다.
        UserByIdQuery userQuery = UserByIdQuery.builder()
                .id(user.getId()).build();
        PostByIdQuery postQuery = PostByIdQuery.builder()
                .id(post.getId()).build();
        MemberCommentCommand.CreateCommand createCommand = MemberCommentCommand.CreateCommand.builder()
                .content("댓글 내용입니다.").build();

        long commentId = memberCommentCommandService.create(userQuery, postQuery, createCommand);

        // 새로운 유저를 만든다.
        User otherUser = UserFixture.aUser()
                .withUsername("other-user")
                .build();

        userRepository.save(otherUser);

        // 새로운 유저로 댓글을 삭제한다.
        // 댓글 작성자가 아니므로, 예외가 발생한다.
        CommentByIdQuery commentQuery = CommentByIdQuery.builder()
                .id(commentId).build();
        DeleteCommand deleteCommand = DeleteCommand.builder()
                .userId(otherUser.getId()).build();

        assertThatThrownBy(() -> memberCommentCommandService.delete(commentQuery, deleteCommand))
                .isInstanceOf(NoAuthorizationException.class);
    }
}
