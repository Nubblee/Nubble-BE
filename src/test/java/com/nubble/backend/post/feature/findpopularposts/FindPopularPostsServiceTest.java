package com.nubble.backend.post.feature.findpopularposts;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.nubble.backend.utils.fixture.domain.UserFixture;
import java.lang.reflect.Field;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class FindPopularPostsServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FindPopularPostsService findPopularPostsService;

    Board board;

    @BeforeEach
    void setUp() {
        // 유저 생성
        User user = UserFixture.aUser().build();
        userRepository.save(user);

        // 게시글 생성
        Category category = Category.builder()
                .name("루트 카테고리")
                .build();
        categoryRepository.save(category);

        board = Board.builder()
                .category(category)
                .name("게시판 이름")
                .build();
        boardRepository.save(board);

        Post post1 = PostFixture.aPublishedPost()
                .board(board)
                .user(user).build();
        Post post2 = PostFixture.aPublishedPost()
                .board(board)
                .user(user).build();
        Post post3 = PostFixture.aPublishedPost()
                .board(board)
                .user(user).build();

        // 리플렉션을 사용하여 likeCount 설정
        setLikeCount(post1, 5);
        setLikeCount(post2, 3);

        postRepository.saveAll(List.of(post1, post2, post3));
    }

    @DisplayName("좋아요가 0 초과인 게시글들을 내림차순으로 가져온다")
    @Test
    void success() {
        // 좋아요 내림차순으로 게시글들을 가져온다
        List<PostWithUserDto> result = findPopularPostsService.findPopularPosts(board.getId());

        // 좋아요가 0 보다 높은 값들만 가져오는지 확인
        // 좋아요 내림차순으로 가져오는지 확인
        int prevLikeCount = Integer.MAX_VALUE;
        for (PostWithUserDto postDto : result) {
            int currentLikeCount = postDto.post().likeCount();

            assertThat(currentLikeCount).isLessThanOrEqualTo(prevLikeCount);
            assertThat(currentLikeCount).isPositive();

            prevLikeCount = currentLikeCount;
        }
    }

    private void setLikeCount(Post post, int likeCount) {
        try {
            Field likeCountField = post.getClass().getDeclaredField("likeCount");

            likeCountField.setAccessible(true);
            likeCountField.set(post, likeCount);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("reflection을 사용한 likeCount 값 설정을 실패하였습니다.");
        }
    }
}
