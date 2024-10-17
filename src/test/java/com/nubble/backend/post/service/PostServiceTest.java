package com.nubble.backend.post.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nubble.backend.board.domain.Board;
import com.nubble.backend.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.customassert.PostAssert;
import com.nubble.backend.fixture.UserFixture;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.service.PostCommand.PostCreateCommand;
import com.nubble.backend.post.service.PostCommand.PostPublishCommand;
import com.nubble.backend.post.shared.PostStatusDto;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private Board board;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    private User user;

    @BeforeEach
    void setup() {
        // 게시글이 속해있는 카테고리와 게시판을 생성한다.
        Category category = Category.builder()
                .name("루트 카테고리")
                .build();
        categoryRepository.save(category);

        board = Board.builder()
                .category(category)
                .name("게시판 이름")
                .build();
        boardRepository.save(board);

        // 게시글을 작성할 유저를 생성한다.
        user = UserFixture.aUser().build();
        userRepository.save(user);
    }

    @DisplayName("임시 상태의 게시글을 생성한다.")
    @Test
    void createPost_shouldCreatePostWithDraftStatus() {
        // given
        PostCreateCommand command = PostCreateCommand.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .userId(user.getId())
                .boardId(board.getId())
                .status(PostStatusDto.DRAFT)
                .build();

        // when
        long newPostId = postService.createPost(command);

        // then
        Optional<Post> postOptional = postRepository.findById(newPostId);

        assertThat(postOptional).isPresent();
        PostAssert.assertThat(postOptional.get())
                .hasId(newPostId)
                .hasTitle(command.title())
                .hasContent(command.content())
                .hasUserId(user.getId())
                .hasStatus(PostStatus.valueOf(command.status().name()))
                .hasThumbnailUrl(null)
                .hasDescription(null);
    }

    @DisplayName("게시 상태의 게시글을 생성한다.")
    @Test
    void createPost_shouldCreatePostWithPublishedStatus() {
        // given
        PostCreateCommand command = PostCreateCommand.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .userId(user.getId())
                .boardId(board.getId())
                .status(PostStatusDto.PUBLISHED)
                .thumbnailUrl("https://example.com")
                .description("요약 내용입니다.")
                .build();

        // when
        long newPostId = postService.createPost(command);

        // then
        Optional<Post> postOptional = postRepository.findById(newPostId);

        assertThat(postOptional).isPresent();
        PostAssert.assertThat(postOptional.get())
                .hasId(newPostId)
                .hasTitle(command.title())
                .hasContent(command.content())
                .hasUserId(user.getId())
                .hasStatus(PostStatus.valueOf(command.status().name()))
                .hasThumbnailUrl(command.thumbnailUrl())
                .hasDescription(command.description());
    }

    @DisplayName("게시글의 주인이 게시글을 게시합니다.")
    @Test
    void test() {
        // given
        PostCreateCommand postCreateCommand = PostCreateCommand.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .userId(user.getId())
                .boardId(board.getId())
                .build();
        long postId = postService.createPost(postCreateCommand);

        PostPublishCommand postPublishCommand = PostPublishCommand.builder()
                .userId(user.getId())
                .postId(postId)
                .thumbnailUrl("https://example.com/thumbnail.jpg")
                .description("설명입니다.")
                .build();

        // when
        PostInfo postInfo = postService.publishPost(postPublishCommand);

        // then
        assertThat(postInfo.postId()).isEqualTo(postId);
        assertThat(postInfo.title()).isEqualTo(postCreateCommand.title());
        assertThat(postInfo.content()).isEqualTo(postCreateCommand.content());
        assertThat(postInfo.userId()).isEqualTo(user.getId());
        assertThat(postInfo.thumbnailUrl()).isEqualTo(postPublishCommand.thumbnailUrl());
        assertThat(postInfo.description()).isEqualTo(postPublishCommand.description());
        assertThat(postInfo.postStatus()).isEqualTo("PUBLISHED");
    }
}
