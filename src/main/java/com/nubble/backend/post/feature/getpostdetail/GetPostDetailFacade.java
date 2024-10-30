package com.nubble.backend.post.feature.getpostdetail;

import com.nubble.backend.category.board.service.BoardInfo.BoardDto;
import com.nubble.backend.category.board.service.BoardService;
import com.nubble.backend.category.service.CategoryInfo.CategoryDto;
import com.nubble.backend.category.service.CategoryService;
import com.nubble.backend.post.feature.PostDto;
import com.nubble.backend.post.feature.getpostdetail.GetPostWithUserService.GetPostWithUserQuery;
import com.nubble.backend.post.feature.getpostdetail.GetPostWithUserService.GetPostWithUserResult;
import com.nubble.backend.user.feature.UserDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPostDetailFacade {

    private final GetPostWithUserService getPostWithUserService;
    private final CategoryService categoryService;
    private final BoardService boardService;

    public GetPostDetailFacadeResult getPostDetailById(GetPostWithUserQuery query) {
        GetPostWithUserResult postWithUser = getPostWithUserService.getPostWithUser(query);
        BoardDto board = boardService.getBoardById(postWithUser.post().boardId());
        CategoryDto category = categoryService.getCategoryById(board.categoryId());

        return GetPostDetailFacadeResult.builder()
                .post(postWithUser.post())
                .user(postWithUser.user())
                .board(board)
                .category(category).build();
    }

    @Builder
    public record GetPostDetailFacadeResult(
            PostDto post,
            UserDto user,
            BoardDto board,
            CategoryDto category
    ) {

    }
}
