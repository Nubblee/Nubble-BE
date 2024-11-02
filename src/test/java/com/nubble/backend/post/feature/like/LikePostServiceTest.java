package com.nubble.backend.post.feature.like;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.feature.like.LikePostService.LikePostCommand;
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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class LikePostServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikePostService likePostService;

    @Autowired
    private PostLikeRepository postLikeRepository;

    private User user;
    private Post publishedPost;
    private Post draftPost;

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

        draftPost = PostFixture.aDraftPost()
                .user(user)
                .board(board).build();
        postRepository.save(draftPost);
    }

    @DisplayName("유저가 게시글에 좋아요를 누른다")
    @Test
    void success() {
        // 게시글에 좋아요를 누른다
        LikePostCommand command = LikePostCommand.builder()
                .postId(publishedPost.getId())
                .userId(user.getId()).build();

        Long postLikeId = likePostService.likePost(command);

        // 좋아요 확인
        Assertions.assertThat(postLikeRepository.findById(postLikeId)).isPresent();
    }

    @DisplayName("임시 게시글에는 좋아요를 누를 수 없다")
    @Test
    void test() {
        // 임시 게시글에 좋아요를 누른다
        // 예외를 발생시킨다
        LikePostCommand command = LikePostCommand.builder()
                .postId(draftPost.getId())
                .userId(user.getId()).build();

        Assertions.assertThatThrownBy(() -> likePostService.likePost(command))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("임시 게시글에는 좋아요를 누를 수 없습니다.");
    }

    @DisplayName("이미 좋아요를 누른 게시글에 다시 좋아요를 누를 수 없다")
    @Test
    void throwException() {
        // 좋아요를 누른 상태로 만든다
        LikePostCommand command = LikePostCommand.builder()
                .postId(publishedPost.getId())
                .userId(user.getId()).build();

        likePostService.likePost(command);

        // 좋아요를 다시 한번 요청한다
        // 예외를 발생시킨다
        Assertions.assertThatThrownBy(() -> likePostService.likePost(command))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 좋아요를 누른 게시글입니다.");
    }
}
