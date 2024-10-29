package com.nubble.backend.comment.delete.member;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.comment.MemberCommentFixture;
import com.nubble.backend.comment.delete.member.DeleteMemberCommentService.DeleteMemberCommentCommand;
import com.nubble.backend.comment.domain.CommentRepository;
import com.nubble.backend.comment.domain.member.MemberComment;
import com.nubble.backend.common.exception.NoAuthorizationException;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.service.PostRepository;
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
class DeleteMemberCommentServiceTest {

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

    @Autowired
    private DeleteMemberCommentService deleteMemberCommentService;

    private User user;
    private MemberComment memberComment;

    @BeforeEach
    void setUp() {
        // 댓글 생성
        Category category = CategoryFixture.aCategory().build();
        categoryRepository.save(category);

        Board board = Board.builder()
                .category(category).build();
        boardRepository.save(board);

        user = UserFixture.aUser().build();
        userRepository.save(user);

        Post post = PostFixture.aPost()
                .withUser(user)
                .withBoard(board)
                .withStatus(PostStatus.PUBLISHED).build();
        postRepository.save(post);

        memberComment = MemberCommentFixture.aMemberComment()
                .post(post)
                .user(user).build();
        commentRepository.save(memberComment);
    }

    @DisplayName("댓글을 삭제한다")
    @Test
    void success() {
        // 댓글 삭제
        DeleteMemberCommentCommand command = DeleteMemberCommentCommand.builder()
                .commentId(memberComment.getId())
                .userId(user.getId()).build();
        deleteMemberCommentService.delete(command);

        // 삭제 확인
        Assertions.assertThat(commentRepository.findById(memberComment.getId())).isEmpty();
    }

    @DisplayName("작성자가 아니라면 삭제할 수 없다")
    @Test
    void throwException() {
        // 댓글 삭제 및 예외 발생
        DeleteMemberCommentCommand command = DeleteMemberCommentCommand.builder()
                .commentId(memberComment.getId())
                .userId(user.getId() + 1).build();
        Assertions.assertThatThrownBy(() -> deleteMemberCommentService.delete(command))
                .isInstanceOf(NoAuthorizationException.class)
                .hasMessage("댓글의 작성자가 아닙니다.");
    }
}
