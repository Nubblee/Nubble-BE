package com.nubble.backend.post.feature.like;

import static org.assertj.core.api.Assertions.assertThat;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.feature.like.LikePostService.LikePostCommand;
import com.nubble.backend.post.fixture.PostFixture;
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
class LikePostFacadeTest {

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

    @DisplayName("좋아요를 누르면 게시글의 좋아요수가 올라간다")
    @Test
    void success() {
        // 게시글에 좋아요를 누른다
        int prevLikeCount = publishedPost.getLikeCount();
        LikePostCommand command = LikePostCommand.builder()
                .postId(publishedPost.getId())
                .userId(user.getId()).build();

        likePostFacade.likePost(command);

        // 게시글에 좋아요 개수가 올라간다
        Post post = postRepository.getPostById(command.postId());
        assertThat(post.getLikeCount()).isEqualTo(prevLikeCount + 1);
    }
}
