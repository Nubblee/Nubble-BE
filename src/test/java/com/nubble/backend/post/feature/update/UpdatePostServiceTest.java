package com.nubble.backend.post.feature.update;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.common.exception.NoAuthorizationException;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.feature.update.UpdatePostService.UpdatePostCommand;
import com.nubble.backend.post.repository.PostRepository;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import com.nubble.backend.utils.customassert.PostAssert;
import com.nubble.backend.utils.fixture.domain.BoardFixture;
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
class UpdatePostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UpdatePostService updatePostService;

    private Post post;
    private Board board;
    private User user;

    @BeforeEach
    void setUp() {
        // 게시글 생성
        Category category = CategoryFixture.aCategory().build();
        categoryRepository.save(category);

        board = BoardFixture.aBoard()
                .category(category).build();
        boardRepository.save(board);

        user = UserFixture.aUser().build();
        userRepository.save(user);

        post = PostFixture.aDraftPost()
                .user(user)
                .board(board).build();
        postRepository.save(post);
    }

    @DisplayName("게시글을 수정한다")
    @Test
    void updatePost() {
        // 게시글 수정
        String titleToUpdate = "수정된 제목";
        String contentToUpdate = "수정된 내용";

        UpdatePostCommand command = UpdatePostCommand.builder()
                .postId(post.getId())
                .title(titleToUpdate)
                .content(contentToUpdate)
                .userId(user.getId())
                .boardId(board.getId())
                .status(PostStatus.DRAFT)
                .build();
        updatePostService.update(command);

        // 수정 확인
        Post updatedPost = postRepository.getPostById(post.getId());
        PostAssert.assertThat(updatedPost)
                .hasTitle(titleToUpdate)
                .hasContent(contentToUpdate);
    }

    @DisplayName("임시 상태 게시글을 게시 상태로 변경한다")
    @Test
    void changeToPublishedPost() {
        // 게시 상태로 변경
        UpdatePostCommand command = UpdatePostCommand.builder()
                .postId(post.getId())
                .title(PostFixture.DEFAULT_TITLE)
                .content(PostFixture.DEFAULT_CONTENT)
                .userId(user.getId())
                .boardId(board.getId())
                .status(PostStatus.PUBLISHED)
                .thumbnailUrl(PostFixture.DEFAULT_THUMBNAIL_URL)
                .description(PostFixture.DEFAULT_DESCRIPTION)
                .build();
        updatePostService.update(command);

        // 게시 상태 확인
        Post updatedPost = postRepository.getPostById(post.getId());
        Assertions.assertThat(updatedPost.getStatus()).isEqualTo(PostStatus.PUBLISHED);
    }

    @DisplayName("게시된 게시글은 임시 상태로 변경되지 않는다")
    @Test
    void notChangeToDraftPost() {
        // 게시 상태로 변경
        UpdatePostCommand commandToPublish = UpdatePostCommand.builder()
                .postId(post.getId())
                .title(PostFixture.DEFAULT_TITLE)
                .content(PostFixture.DEFAULT_CONTENT)
                .userId(user.getId())
                .boardId(board.getId())
                .status(PostStatus.PUBLISHED)
                .thumbnailUrl(PostFixture.DEFAULT_THUMBNAIL_URL)
                .description(PostFixture.DEFAULT_DESCRIPTION)
                .build();
        updatePostService.update(commandToPublish);

        // 임시 상태로 변경
        UpdatePostCommand commandToDraft = UpdatePostCommand.builder()
                .postId(post.getId())
                .title(PostFixture.DEFAULT_TITLE)
                .content(PostFixture.DEFAULT_CONTENT)
                .userId(user.getId())
                .boardId(board.getId())
                .status(PostStatus.DRAFT)
                .build();
        updatePostService.update(commandToDraft);

        // 임시 상태 확인
        Post updatedPost = postRepository.getPostById(post.getId());
        Assertions.assertThat(updatedPost.getStatus()).isEqualTo(PostStatus.PUBLISHED);
    }

    @DisplayName("게시글의 작성자가 아니라면 게시글을 수정할 수 없다")
    @Test
    void throwException_notAuthor() {
        // 다른 사용자가 게시글을 수정
        User otherUser = UserFixture.aUser()
                .username("other").build();
        userRepository.save(otherUser);

        UpdatePostCommand command = UpdatePostCommand.builder()
                .postId(post.getId())
                .title(PostFixture.DEFAULT_TITLE)
                .content(PostFixture.DEFAULT_CONTENT)
                .userId(otherUser.getId())
                .boardId(board.getId())
                .status(PostStatus.DRAFT)
                .build();

        // 예외 발생
        Assertions.assertThatThrownBy(() -> updatePostService.update(command))
                .isInstanceOf(NoAuthorizationException.class)
                .hasMessage("게시글의 작성자가 아닙니다.");
    }
}
