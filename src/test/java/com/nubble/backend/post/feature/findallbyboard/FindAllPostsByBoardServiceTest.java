package com.nubble.backend.post.feature.findallbyboard;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.feature.PostWithUserDto;
import com.nubble.backend.post.fixture.PostFixture;
import com.nubble.backend.post.repository.PostRepository;
import com.nubble.backend.userold.domain.User;
import com.nubble.backend.userold.service.UserRepository;
import com.nubble.backend.utils.fixture.domain.BoardFixture;
import com.nubble.backend.utils.fixture.domain.UserFixture;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class FindAllPostsByBoardServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FindAllPostsByBoardService findAllPostsByBoardService;

    private Category category;
    private Board board;
    private User user;

    @BeforeEach
    void setUp() {
        // 게시판 생성
        category = Category.builder()
                .name("루트 카테고리")
                .build();
        categoryRepository.save(category);

        board = Board.builder()
                .category(category)
                .name("게시판 이름")
                .build();
        boardRepository.save(board);

        // 유저 생성
        user = UserFixture.aUser().build();
        userRepository.save(user);
    }

    @DisplayName("게시판에 있는 게시글들을 가져온다")
    @Test
    void success_onlyOwn() {
        // 게시글 생성
        int postCount = 5;
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < postCount; i++) {
            Post post = PostFixture.aPublishedPost()
                    .board(board)
                    .user(user)
                    .build();
            posts.add(post);
        }
        postRepository.saveAll(posts);

        // 다른 게시판에 글 생성
        // 다른 게시판에 게시글을 작성한다
        Board otherBoard = BoardFixture.aBoard().category(category)
                .build();
        boardRepository.save(otherBoard);

        int otherPostCount = 3;
        List<Post> otherPosts = new ArrayList<>();
        for (int i = 0; i < otherPostCount; i++) {
            Post post = PostFixture.aPublishedPost()
                    .board(otherBoard)
                    .user(user).build();
            otherPosts.add(post);
        }
        postRepository.saveAll(otherPosts);

        // 게시글 조회
        List<PostWithUserDto> result = findAllPostsByBoardService.findAllByBoardId(board.getId());

        // 게시글 검증
        Assertions.assertThat(result).hasSize(postCount)
                .allMatch(post -> Objects.equals(post.post().boardId(), board.getId()));
    }

    @DisplayName("게시판의 글을 가져올 때, 임시글은 가져오지 않는다")
    @Test
    void success_notDraft() {
        // 임시글 생성
        int postCount = 5;
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < postCount; i++) {
            Post post = PostFixture.aDraftPost()
                    .board(board)
                    .user(user).build();
            posts.add(post);
        }
        postRepository.saveAll(posts);

        // 게시판의 글 조회
        List<PostWithUserDto> result = findAllPostsByBoardService.findAllByBoardId(board.getId());

        // 임시 게시글 제외 확인
        Assertions.assertThat(result).isEmpty();
    }
}
