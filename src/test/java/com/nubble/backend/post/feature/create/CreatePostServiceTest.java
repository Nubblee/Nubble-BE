package com.nubble.backend.post.feature.create;

import static org.assertj.core.api.Assertions.assertThat;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.feature.create.CreatePostService.CreatePostCommand;
import com.nubble.backend.post.repository.PostRepository;
import com.nubble.backend.userold.domain.User;
import com.nubble.backend.userold.service.UserRepository;
import com.nubble.backend.utils.fixture.domain.BoardFixture;
import com.nubble.backend.utils.fixture.domain.CategoryFixture;
import com.nubble.backend.post.fixture.PostFixture;
import com.nubble.backend.utils.fixture.domain.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CreatePostServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreatePostService createPostService;

    @Autowired
    private PostRepository postRepository;

    private Board board;
    private User user;

    @BeforeEach
    void setUp() {
        Category category = CategoryFixture.aCategory().build();
        categoryRepository.save(category);

        board = BoardFixture.aBoard()
                .category(category).build();
        boardRepository.save(board);

        user = UserFixture.aUser().build();
        userRepository.save(user);
    }

    @DisplayName("임시 상태의 글을 생성한다")
    @Test
    void success_createDraftPost() {
        // 임시 게시글 생성
        CreatePostCommand command = CreatePostCommand.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .userId(user.getId())
                .boardId(board.getId())
                .status(PostStatus.DRAFT).build();
        long newPostId = createPostService.create(command);

        // 생성된 임시 게시글 확인
        assertThat(postRepository.findById(newPostId)).isPresent();
    }

    @DisplayName("게시 상태의 글을 생성한다")
    @Test
    void success_createPublishedPost() {
        // 게시 상태의 글 생성
        CreatePostCommand command = CreatePostCommand.builder()
                .title(PostFixture.DEFAULT_TITLE)
                .content(PostFixture.DEFAULT_CONTENT)
                .userId(user.getId())
                .boardId(board.getId())
                .status(PostStatus.PUBLISHED)
                .thumbnailUrl(PostFixture.DEFAULT_THUMBNAIL_URL)
                .description(PostFixture.DEFAULT_DESCRIPTION).build();
        long newPostId = createPostService.create(command);

        // 생성된 글 확인
        assertThat(postRepository.findById(newPostId)).isPresent();
    }
}
