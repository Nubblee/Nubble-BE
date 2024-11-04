package com.nubble.backend.post.feature.unlinke;

import static org.assertj.core.api.Assertions.assertThat;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.feature.like.LikePostFacade;
import com.nubble.backend.post.feature.like.LikePostService;
import com.nubble.backend.post.feature.like.LikePostService.LikePostCommand;
import com.nubble.backend.post.feature.unlinke.UnlikePostService.UnlikePostCommand;
import com.nubble.backend.post.fixture.PostFixture;
import com.nubble.backend.post.repository.PostLikeRepository;
import com.nubble.backend.post.repository.PostRepository;
import com.nubble.backend.userold.domain.User;
import com.nubble.backend.userold.service.UserRepository;
import com.nubble.backend.utils.fixture.domain.BoardFixture;
import com.nubble.backend.utils.fixture.domain.CategoryFixture;
import com.nubble.backend.utils.fixture.domain.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UnlikePostFacadeTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikePostFacade likePostFacade;

    @Autowired
    private UnlikePostFacade unlikePostFacade;

    @Autowired
    private LikePostService likePostService;

    @Autowired
    private PostLikeRepository postLikeRepository;

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
        // 좋아요를 누른다
        LikePostCommand likePostCommand = LikePostCommand.builder()
                .postId(publishedPost.getId())
                .userId(user.getId()).build();
        likePostFacade.likePost(likePostCommand);

        // 좋아요를 취소한다
        UnlikePostCommand unlikePostCommand = UnlikePostCommand.builder()
                .postId(publishedPost.getId())
                .userId(user.getId()).build();
        unlikePostFacade.unlikePost(unlikePostCommand);

        // 취소되었는지 확인
        Post post = postRepository.getPostById(publishedPost.getId());
        assertThat(post.getLikeCount()).isZero();
    }

    @DisplayName("좋아요가 0이하로 내려가지 않는다")
    @Test
    void not_fall_below_zero() {
        // 강제로 좋아요는 누르지만, 좋아요 카운트는 증가시키지 않는다
        LikePostCommand likePostCommand = LikePostCommand.builder()
                .postId(publishedPost.getId())
                .userId(user.getId()).build();
        Long postLikeId = likePostService.likePost(likePostCommand);

        assertThat(postLikeRepository.findById(postLikeId)).isPresent();
        assertThat(postRepository.getPostById(publishedPost.getId()).getLikeCount()).isZero();

        // 좋아요를 취소해도 0이하로 떨어지지 않는다
        UnlikePostCommand unlikePostCommand = UnlikePostCommand.builder()
                .postId(publishedPost.getId())
                .userId(user.getId()).build();
        unlikePostFacade.unlikePost(unlikePostCommand);

        assertThat(postLikeRepository.findById(postLikeId)).isEmpty();
        assertThat(postRepository.getPostById(publishedPost.getId()).getLikeCount()).isZero();
    }
}
