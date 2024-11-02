package com.nubble.backend.post.feature.unlinke;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostLike;
import com.nubble.backend.post.feature.unlinke.UnlikePostService.UnlikePostCommand;
import com.nubble.backend.post.fixture.PostFixture;
import com.nubble.backend.post.repository.PostLikeRepository;
import com.nubble.backend.post.repository.PostRepository;
import com.nubble.backend.userold.domain.User;
import com.nubble.backend.userold.service.UserRepository;
import com.nubble.backend.utils.fixture.domain.BoardFixture;
import com.nubble.backend.utils.fixture.domain.CategoryFixture;
import com.nubble.backend.utils.fixture.domain.UserFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UnlikePostServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private UnlikePostService unlikePostService;

    private User user;
    private Post publishedPost;

    @BeforeEach
    void setUp() {
        Category category = CategoryFixture.aCategory().build();
        categoryRepository.save(category);

        Board board = BoardFixture.aBoard()
                .category(category).build();
        boardRepository.save(board);

        user = UserFixture.aUser().build();
        userRepository.save(user);

        publishedPost = PostFixture.aPublishedPost()
                .user(user)
                .board(board).build();
        postRepository.save(publishedPost);
    }

    @DisplayName("좋아요를 취소한다")
    @Test
    void success() {
        // 좋아요를 한다
        PostLike postLike = PostLike.builder()
                .post(publishedPost)
                .user(user).build();
        Long postLikeId = postLikeRepository.save(postLike).getId();

        // 좋아요를 취소한다
        UnlikePostCommand command = UnlikePostCommand.builder()
                .postId(publishedPost.getId())
                .userId(user.getId()).build();

        unlikePostService.unlikePost(command);

        // 좋아요 취소를 확인한다
        Assertions.assertThat(postLikeRepository.findById(postLikeId)).isEmpty();
    }

    @DisplayName("좋아요를 누른 적이 없이, 취소할 수 없다")
    @Test
    void throwException() {
        // 좋아요 취소를 한다
        // 좋아요 한 적이 없으므로 예외를 발생시킨다
        UnlikePostCommand command = UnlikePostCommand.builder()
                .postId(publishedPost.getId())
                .userId(user.getId()).build();

        Assertions.assertThatThrownBy(() -> unlikePostService.unlikePost(command))
                .isInstanceOf(JpaObjectRetrievalFailureException.class)
                .hasMessage("좋아요가 존재하지 않습니다.");
    }
}
