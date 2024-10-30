package com.nubble.backend.post.feature.getpostdetail;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.common.exception.NoAuthorizationException;
import com.nubble.backend.post.customassert.PostDtoAssert;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.feature.PostDto;
import com.nubble.backend.post.feature.getpostdetail.GetPostWithUserService.GetPostWithUserQuery;
import com.nubble.backend.post.feature.getpostdetail.GetPostWithUserService.GetPostWithUserResult;
import com.nubble.backend.post.fixture.PostFixture;
import com.nubble.backend.post.repository.PostRepository;
import com.nubble.backend.userold.domain.User;
import com.nubble.backend.userold.service.UserRepository;
import com.nubble.backend.utils.fixture.domain.UserFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class GetPostWithUserServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private GetPostWithUserService getPostWithUserService;

    private User user;
    private Board board;

    @BeforeEach
    void setUp() {
        // 게시판 생성
        Category category = Category.builder()
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

    @DisplayName("게시글과 유저의 정보를 알려준다")
    @Test
    void success() {
        // 게시글 생성
        Post post = PostFixture.aPublishedPost()
                .user(user)
                .board(board).build();
        postRepository.save(post);

        PostDto expectedPostDto = PostDto.fromDomain(post);

        // 게시글 조회
        GetPostWithUserQuery query = GetPostWithUserQuery.builder()
                .postId(post.getId()).build();
        GetPostWithUserResult result = getPostWithUserService.getPostWithUser(query);

        // 게시글 내용 확인
        PostDtoAssert.assertThat(result.post())
                .isEqualTo(expectedPostDto);
    }

    @DisplayName("임시글이면서, 작성자라면 임시글의 내용을 볼 수 있다")
    @Test
    void success_author() {
        // 게시글 생성
        Post post = PostFixture.aDraftPost()
                .user(user)
                .board(board).build();
        postRepository.save(post);

        PostDto expectedPostDto = PostDto.fromDomain(post);

        // 임시글 조회 및 예외 발생
        GetPostWithUserQuery query = GetPostWithUserQuery.builder()
                .postId(post.getId())
                .userId(user.getId()).build();
        GetPostWithUserResult result = getPostWithUserService.getPostWithUser(query);

        // 게시글 내용 조회
        PostDtoAssert.assertThat(result.post())
                .isEqualTo(expectedPostDto);
    }

    @DisplayName("임시 글이면서, 작성자가 아니라면 게시글을 읽을 수 없다")
    @Test
    void throwException_draftPostAndNotAuthor() {
        // 게시글 생성
        Post post = PostFixture.aDraftPost()
                .user(user)
                .board(board).build();
        postRepository.save(post);

        // 임시글 조회 및 예외 발생
        GetPostWithUserQuery query = GetPostWithUserQuery.builder()
                .postId(post.getId()).build();
        Assertions.assertThatThrownBy(() -> getPostWithUserService.getPostWithUser(query))
                .isInstanceOf(NoAuthorizationException.class)
                .hasMessage("글이 공개되지 않은 상태입니다.");
    }

    @DisplayName("존재하지 않는 게시글 접근 시, 예외가 발생한다")
    @Test
    void throwException_nonExistentPost() {
        GetPostWithUserQuery query = GetPostWithUserQuery.builder()
                .postId(1L).build();
        Assertions.assertThatThrownBy(() -> getPostWithUserService.getPostWithUser(query))
                .isInstanceOf(DataAccessException.class)
                .hasMessage("게시글이 존재하지 않습니다.");
    }
}
