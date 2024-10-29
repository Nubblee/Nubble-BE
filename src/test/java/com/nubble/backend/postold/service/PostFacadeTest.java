package com.nubble.backend.postold.service;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardInfo.BoardDto;
import com.nubble.backend.category.board.service.BoardInfoMapper;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryInfo;
import com.nubble.backend.category.service.CategoryInfoMapper;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.postold.domain.Post;
import com.nubble.backend.postold.service.PostInfo.PostDto;
import com.nubble.backend.postold.service.PostInfo.PostWithCategoryDto;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserInfo.UserDto;
import com.nubble.backend.user.service.UserInfoMapper;
import com.nubble.backend.user.service.UserRepository;
import com.nubble.backend.utils.customassert.BoardDtoAssert;
import com.nubble.backend.utils.customassert.CategoryDtoAssert;
import com.nubble.backend.utils.customassert.PostDtoAssert;
import com.nubble.backend.utils.customassert.UserDtoAssert;
import com.nubble.backend.utils.fixture.domain.BoardFixture;
import com.nubble.backend.utils.fixture.domain.CategoryFixture;
import com.nubble.backend.utils.fixture.domain.PostFixture;
import com.nubble.backend.utils.fixture.domain.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PostFacadeTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostFacade postFacade;

    @Autowired
    private PostInfoMapper postInfoMapper;

    @Autowired
    private CategoryInfoMapper categoryInfoMapper;

    @Autowired
    private BoardInfoMapper boardInfoMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @DisplayName("게시글 조회시, Post, Board, Category의 정보를 가져온다")
    @Test
    void test() {
        // Post, Board, Category 데이터 생성
        Category category = CategoryFixture.aCategory().build();
        categoryRepository.save(category);
        Board board = BoardFixture.aBoard()
                .category(category).build();
        boardRepository.save(board);
        User user = UserFixture.aUser().build();
        userRepository.save(user);
        Post post = PostFixture.aPost()
                .user(user).board(board).build();
        postRepository.save(post);

        PostDto expectedPostDto = postInfoMapper.toPostDto(post);
        UserDto expectedUserDto = userInfoMapper.toUserInfo(user);
        BoardDto expectedBoardDto = boardInfoMapper.toBoardDto(board);
        CategoryInfo.CategoryDto expectedCategoryDto = categoryInfoMapper.toCategoryDto(category);

        // PostFacade 게시글 조회
        PostWithCategoryDto result = postFacade.getPostById(post.getId());

        // Post, User, Board, Category 데이터 검증
        PostDtoAssert.assertThat(result.postWithUserDto().post())
                .isEqualTo(expectedPostDto);
        UserDtoAssert.assertThat(result.postWithUserDto().user())
                .isEqualTo(expectedUserDto);
        BoardDtoAssert.assertThat(result.board())
                .isEqualTo(expectedBoardDto);
        CategoryDtoAssert.assertThat(result.category())
                .isEqualTo(expectedCategoryDto);
    }
}
