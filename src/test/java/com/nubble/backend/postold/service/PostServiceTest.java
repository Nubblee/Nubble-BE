package com.nubble.backend.postold.service;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.post.repository.PostRepository;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import com.nubble.backend.utils.fixture.domain.UserFixture;
import org.junit.jupiter.api.BeforeEach;
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

//    @DisplayName("boardId와 매핑되어 있는 게시글들을 가져온다")
//    @Test
//    void findPostsByBoardId_ShouldReturnPostsMappedToBoardId() {
//        // 가져오려는 게시판에 게시글을 작성한다
//        int postCount = 5;
//        List<Post> posts = new ArrayList<>();
//        for (int i = 0; i < postCount; i++) {
//            Post post = PostFixture.aPublishedPost()
//                    .board(board)
//                    .user(user)
//                    .build();
//            posts.add(post);
//        }
//        postRepository.saveAll(posts);
//
//        // 다른 게시판에 게시글을 작성한다
//        Board otherBoard = BoardFixture.aBoard().category(category)
//                .build();
//        boardRepository.save(otherBoard);
//
//        int otherPostCount = 3;
//        List<Post> otherPosts = new ArrayList<>();
//        for (int i = 0; i < otherPostCount; i++) {
//            Post post = PostFixture.aPublishedPost()
//                    .board(otherBoard)
//                    .user(user)
//                    .build();
//            otherPosts.add(post);
//        }
//        postRepository.saveAll(otherPosts);
//
//        // 게시판과 매핑된 게시글을 가져온다
//        List<PostWithUserDto> postsByBoardId = postService.findPostsByBoardId(board.getId());
//
//        // 매핑된 게시글들만 가져오는 것을 검증한다
//        assertThat(postsByBoardId).hasSize(postCount)
//                .allMatch(post -> post.post().boardId() == board.getId());
//    }
//
//    @DisplayName("게시글을 조회할 때, 임시 게시글은 가져오지 않는다")
//    @Test
//    void findPostsByBoardId_ShouldReturnOnlyNonDraftPosts() {
//        // 가져오려는 게시판에 게시글을 작성한다
//        int postCount = 5;
//        List<Post> posts = new ArrayList<>();
//        for (int i = 0; i < postCount; i++) {
//            Post post = PostFixture.aDraftPost()
//                    .board(board)
//                    .user(user)
//                    .build();
//            posts.add(post);
//        }
//        postRepository.saveAll(posts);
//
//        // 게시판과 매핑된 게시글을 가져온다
//        List<PostWithUserDto> postsByBoardId = postService.findPostsByBoardId(board.getId());
//
//        // 매핑된 게시글들만 가져오는 것을 검증한다
//        assertThat(postsByBoardId).isEmpty();
//    }
//
//    @DisplayName("원하는 게시글과 작성자의 정보를 조회한다")
//    @Test
//    void getPostById_success() {
//        // 게시글을 생성한다
//        Post post = PostFixture.aPublishedPost()
//                .board(board)
//                .user(user)
//                .build();
//        postRepository.save(post);
//        PostDto actualPostDto = postInfoMapper.toPostDto(post);
//        UserDto acutualUserDto = UserDto.builder()
//                .nickname(user.getNickname())
//                .username(user.getUsername()).build();
//
//        // 게시글을 조회한다
//        PostWithUserDto result = postService.getPostById(post.getId());
//
//        // 게시글을 검증한다
//        PostDtoAssert.assertThat(actualPostDto)
//                .isEqualTo(result.post());
//        // 유저를 검증한다
//        UserDtoAssert.assertThat(acutualUserDto)
//                .isEqualTo(result.user());
//    }
//
//    @DisplayName("임시 게시글은 가져올 수 없다")
//    @Test
//    void getPostById_throwException() {
//        // 임시 게시글을 생성한다
//        Post post = PostFixture.aDraftPost()
//                .board(board)
//                .user(user)
//                .build();
//        postRepository.save(post);
//        Long postId = post.getId();
//
//        // 게시글을 조회시, 예외를 발생시킨다
//        Assertions.assertThatThrownBy(() -> postService.getPostById(postId))
//                .isInstanceOf(RuntimeException.class);
//    }
//
//    @DisplayName("존재하지 않는 게시글은 가져올 수 없다")
//    @Test
//    void getPostById_throwException2() {
//        // 게시글을 조회시, 예외를 발생시킨다
//        Assertions.assertThatThrownBy(() -> postService.getPostById(1L))
//                .isInstanceOf(RuntimeException.class);
//    }
}
