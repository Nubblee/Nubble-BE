package com.nubble.backend.post.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.service.PostCommand.PostCreateCommand;
import com.nubble.backend.post.service.PostCommand.PostUpdateCommand;
import com.nubble.backend.post.service.PostInfo.PostDto;
import com.nubble.backend.post.service.PostInfo.PostWithUserDto;
import com.nubble.backend.post.shared.PostStatusDto;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserInfo.UserDto;
import com.nubble.backend.user.service.UserRepository;
import com.nubble.backend.utils.customassert.PostAssert;
import com.nubble.backend.utils.customassert.PostDtoAssert;
import com.nubble.backend.utils.customassert.UserDtoAssert;
import com.nubble.backend.utils.fixture.domain.BoardFixture;
import com.nubble.backend.utils.fixture.domain.PostFixture;
import com.nubble.backend.utils.fixture.domain.UserFixture;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
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


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    Category category;
    private Board board;
    private User user;
    @Autowired
    private PostInfoMapper postInfoMapper;

    @BeforeEach
    void setup() {
        // 게시글이 속해있는 카테고리와 게시판을 생성한다
        category = Category.builder()
                .name("루트 카테고리")
                .build();
        categoryRepository.save(category);

        board = Board.builder()
                .category(category)
                .name("게시판 이름")
                .build();
        boardRepository.save(board);

        // 게시글을 작성할 유저를 생성한다
        user = UserFixture.aUser().build();
        userRepository.save(user);
    }

    @DisplayName("임시 상태의 게시글을 생성한다")
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

    @DisplayName("게시 상태의 게시글을 생성한다")
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

    @DisplayName("게시글의 주인이 임시 게시글을 수정합니다")
    @Test
    void updatePost_shouldUpdatePost() {
        // 임시 게시글을 생성한다
        PostCreateCommand postCreateCommand = PostCreateCommand.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .userId(user.getId())
                .boardId(board.getId())
                .status(PostStatusDto.DRAFT)
                .thumbnailUrl("https://example.com")
                .description("요약 내용입니다.")
                .build();
        long postId = postService.createPost(postCreateCommand);

        // 임시 게시글의 내용을 수정한다
        PostUpdateCommand postUpdateCommand = PostUpdateCommand.builder()
                .postId(postId)
                .title("수정된 제목")
                .content("수정된 내용")
                .userId(user.getId())
                .boardId(board.getId())
                .status(PostStatusDto.DRAFT)
                .thumbnailUrl("https://example.com22")
                .description("수정된 요약 내용")
                .build();

        postService.updatePost(postUpdateCommand);

        // 업데이트된 내용을 검증한다
        Optional<Post> postOptional = postRepository.findById(postId);

        assertThat(postOptional).isPresent();
        PostAssert.assertThat(postOptional.get())
                .hasId(postUpdateCommand.postId())
                .hasTitle(postUpdateCommand.title())
                .hasContent(postUpdateCommand.content())
                .hasStatus(PostStatus.valueOf(PostStatusDto.DRAFT.name()))
                .hasThumbnailUrl(postUpdateCommand.thumbnailUrl())
                .hasDescription(postUpdateCommand.description());
    }

    @DisplayName("임시 게시글을 게시한다")
    @Test
    void updatePost_shouldBePublished() {
        // 임시 게시글을 생성한다
        PostCreateCommand postCreateCommand = PostCreateCommand.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .userId(user.getId())
                .boardId(board.getId())
                .status(PostStatusDto.DRAFT)
                .thumbnailUrl("https://example.com")
                .description("요약 내용입니다.")
                .build();
        long postId = postService.createPost(postCreateCommand);

        // 게시글을 게시한다
        PostUpdateCommand postUpdateCommand = PostUpdateCommand.builder()
                .postId(postId)
                .title(postCreateCommand.title())
                .content(postCreateCommand.content())
                .userId(user.getId())
                .boardId(board.getId())
                .status(PostStatusDto.PUBLISHED)
                .thumbnailUrl(postCreateCommand.thumbnailUrl())
                .description(postCreateCommand.description())
                .build();

        postService.updatePost(postUpdateCommand);

        // 게시되었는지 검증한다
        Optional<Post> postOptional = postRepository.findById(postId);

        assertThat(postOptional).isPresent();
        PostAssert.assertThat(postOptional.get())
                .hasId(postUpdateCommand.postId())
                .hasStatus(PostStatus.valueOf(PostStatusDto.PUBLISHED.name()));
    }

    @DisplayName("게시글이 주인이 아니라면, 게시글을 업데이트할 수 없다")
    @Test
    void updatePost_ShouldThrowException_whenNonPostOwner() {
        // 임시 게시글을 생성한다
        PostCreateCommand postCreateCommand = PostCreateCommand.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .userId(user.getId())
                .boardId(board.getId())
                .status(PostStatusDto.DRAFT)
                .thumbnailUrl("https://example.com")
                .description("요약 내용입니다.")
                .build();
        long postId = postService.createPost(postCreateCommand);

        long nonPostOwnerId = user.getId() + 1;
        PostUpdateCommand postUpdateCommand = PostUpdateCommand.builder()
                .postId(postId)
                .title(postCreateCommand.title())
                .content(postCreateCommand.content())
                .userId(nonPostOwnerId)
                .boardId(board.getId())
                .status(PostStatusDto.DRAFT)
                .thumbnailUrl(postCreateCommand.thumbnailUrl())
                .description(postCreateCommand.description())
                .build();

        // 게시글을 업데이트하지만, 게시글의 주인이 아니므로 예외를 발생시킨다
        Assertions.assertThatThrownBy(() -> postService.updatePost(postUpdateCommand))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("게시글의 주인이 아닙니다.");
    }

    @DisplayName("boardId와 매핑되어 있는 게시글들을 가져온다")
    @Test
    void findPostsByBoardId_ShouldReturnPostsMappedToBoardId() {
        // 가져오려는 게시판에 게시글을 작성한다
        int postCount = 5;
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < postCount; i++) {
            Post post = PostFixture.aPost()
                    .board(board)
                    .user(user)
                    .build();
            posts.add(post);
        }
        postRepository.saveAll(posts);

        // 다른 게시판에 게시글을 작성한다
        Board otherBoard = BoardFixture.aBoard().category(category)
                .build();
        boardRepository.save(otherBoard);

        int otherPostCount = 3;
        List<Post> otherPosts = new ArrayList<>();
        for (int i = 0; i < otherPostCount; i++) {
            Post post = PostFixture.aPost()
                    .board(otherBoard)
                    .user(user)
                    .build();
            otherPosts.add(post);
        }
        postRepository.saveAll(otherPosts);

        // 게시판과 매핑된 게시글을 가져온다
        List<PostWithUserDto> postsByBoardId = postService.findPostsByBoardId(board.getId());

        // 매핑된 게시글들만 가져오는 것을 검증한다
        assertThat(postsByBoardId).hasSize(postCount)
                .allMatch(post -> post.post().boardId() == board.getId());
    }

    @DisplayName("게시글을 조회할 때, 임시 게시글은 가져오지 않는다")
    @Test
    void findPostsByBoardId_ShouldReturnOnlyNonDraftPosts() {
        // 가져오려는 게시판에 게시글을 작성한다
        int postCount = 5;
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < postCount; i++) {
            Post post = PostFixture.aPost()
                    .board(board)
                    .user(user)
                    .status(PostStatus.DRAFT)
                    .build();
            posts.add(post);
        }
        postRepository.saveAll(posts);

        // 게시판과 매핑된 게시글을 가져온다
        List<PostWithUserDto> postsByBoardId = postService.findPostsByBoardId(board.getId());

        // 매핑된 게시글들만 가져오는 것을 검증한다
        assertThat(postsByBoardId).isEmpty();
    }

    @DisplayName("원하는 게시글과 작성자의 정보를 조회한다")
    @Test
    void getPostById_success() {
        // 게시글을 생성한다
        Post post = PostFixture.aPost()
                .board(board)
                .user(user)
                .build();
        postRepository.save(post);
        PostDto actualPostDto = postInfoMapper.toPostDto(post);
        UserDto acutualUserDto = UserDto.builder()
                .nickname(user.getNickname())
                .username(user.getUsername()).build();

        // 게시글을 조회한다
        PostWithUserDto result = postService.getPostById(post.getId());

        // 게시글을 검증한다
        PostDtoAssert.assertThat(actualPostDto)
                .isEqualTo(result.post());
        // 유저를 검증한다
        UserDtoAssert.assertThat(acutualUserDto)
                .isEqualTo(result.user());
    }

    @DisplayName("임시 게시글은 가져올 수 없다")
    @Test
    void getPostById_throwException() {
        // 임시 게시글을 생성한다
        Post post = PostFixture.aPost()
                .board(board)
                .user(user)
                .status(PostStatus.DRAFT)
                .build();
        postRepository.save(post);
        Long postId = post.getId();

        // 게시글을 조회시, 예외를 발생시킨다
        Assertions.assertThatThrownBy(() -> postService.getPostById(postId))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("존재하지 않는 게시글은 가져올 수 없다")
    @Test
    void getPostById_throwException2() {
        // 게시글을 조회시, 예외를 발생시킨다
        Assertions.assertThatThrownBy(() -> postService.getPostById(1L))
                .isInstanceOf(RuntimeException.class);
    }
}
