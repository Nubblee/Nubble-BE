package com.nubble.backend.post.feature.getpostdetail;

import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardInfo.BoardDto;
import com.nubble.backend.category.board.service.BoardInfoMapper;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryInfo;
import com.nubble.backend.category.service.CategoryInfoMapper;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.post.customassert.PostDtoAssert;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.feature.PostDto;
import com.nubble.backend.post.feature.getpostdetail.GetPostDetailFacade.GetPostDetailFacadeInfo;
import com.nubble.backend.post.feature.getpostdetail.GetPostWithUserService.GetPostWithUserQuery;
import com.nubble.backend.post.fixture.PostFixture;
import com.nubble.backend.post.repository.PostRepository;
import com.nubble.backend.user.customassert.UserDtoAssert;
import com.nubble.backend.user.feature.UserDto;
import com.nubble.backend.userold.domain.User;
import com.nubble.backend.userold.service.UserRepository;
import com.nubble.backend.utils.customassert.BoardDtoAssert;
import com.nubble.backend.utils.customassert.CategoryDtoAssert;
import com.nubble.backend.utils.fixture.domain.BoardFixture;
import com.nubble.backend.utils.fixture.domain.CategoryFixture;
import com.nubble.backend.utils.fixture.domain.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class GetPostDetailFacadeTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private GetPostDetailFacade getPostDetailFacade;

    @Autowired
    private BoardInfoMapper boardInfoMapper;

    @Autowired
    private CategoryInfoMapper categoryInfoMapper;

    @DisplayName("게시글 상세 조회시, post, board, user, category 정보를 가져온다")
    @Test
    void success() {
        // post, board, user, category 생성
        Category category = CategoryFixture.aCategory().build();
        categoryRepository.save(category);

        Board board = BoardFixture.aBoard()
                .category(category).build();
        boardRepository.save(board);

        User user = UserFixture.aUser().build();
        userRepository.save(user);

        Post post = PostFixture.aPublishedPost()
                .user(user).board(board).build();
        postRepository.save(post);

        PostDto postDto = PostDto.fromDomain(post);
        UserDto userDto = UserDto.fromDomain(user);
        BoardDto expectedBoardDto = boardInfoMapper.toBoardDto(board);
        CategoryInfo.CategoryDto expectedCategoryDto = categoryInfoMapper.toCategoryDto(category);

        // 게시글 상세 조회
        GetPostWithUserQuery query = GetPostWithUserQuery.builder()
                .postId(post.getId()).build();
        GetPostDetailFacadeInfo actualResult = getPostDetailFacade.getPostDetailById(query);

        // 값 검증
        PostDtoAssert.assertThat(actualResult.post())
                .isEqualTo(postDto);
        UserDtoAssert.assertThat(actualResult.user())
                .isEqualTo(userDto);
        BoardDtoAssert.assertThat(actualResult.board())
                .isEqualTo(expectedBoardDto);
        CategoryDtoAssert.assertThat(actualResult.category())
                .isEqualTo(expectedCategoryDto);
    }
}
