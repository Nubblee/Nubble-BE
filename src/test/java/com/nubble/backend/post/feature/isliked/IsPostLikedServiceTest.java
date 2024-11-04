package com.nubble.backend.post.feature.isliked;

import static org.assertj.core.api.Assertions.assertThat;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.domain.PostLike;
import com.nubble.backend.post.feature.isliked.IsPostLikedService.IsPostLikedQuery;
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
class IsPostLikedServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IsPostLikedService isPostLikedService;

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

    @DisplayName("게시글에 좋아요를 눌렀다면 true를 반환한다")
    @Test
    void sucess_returnTrue() {
        // 게시글 좋아요를 누른다
        PostLike postLike = PostLike.builder()
                .post(publishedPost)
                .user(user).build();
        postLikeRepository.save(postLike);

        // 게시글 좋아요 검사
        IsPostLikedQuery query = IsPostLikedQuery.builder()
                .postId(publishedPost.getId())
                .userId(user.getId()).build();
        boolean result = isPostLikedService.isPostLiked(query);

        assertThat(result).isTrue();
    }

    @DisplayName("게시글에 좋아요를 누르지 않았다면 false를 반환한다")
    @Test
    void sucess_returnFalse() {
        // 게시글 좋아요 검사
        IsPostLikedQuery query = IsPostLikedQuery.builder()
                .postId(publishedPost.getId())
                .userId(user.getId()).build();
        boolean result = isPostLikedService.isPostLiked(query);

        assertThat(result).isFalse();
    }
}
