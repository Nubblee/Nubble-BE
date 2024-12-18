package com.nubble.backend.post.feature.getpostdetail;

import com.nubble.backend.category.board.service.BoardInfo.BoardDto;
import com.nubble.backend.category.board.service.BoardService;
import com.nubble.backend.category.service.CategoryInfo.CategoryDto;
import com.nubble.backend.category.service.CategoryService;
import com.nubble.backend.post.feature.PostDto;
import com.nubble.backend.post.feature.PostWithUserDto;
import com.nubble.backend.post.feature.getpostdetail.GetPostWithUserService.GetPostWithUserQuery;
import com.nubble.backend.post.feature.isliked.IsPostLikedQueryMapper;
import com.nubble.backend.post.feature.isliked.IsPostLikedService;
import com.nubble.backend.post.feature.isliked.IsPostLikedService.IsPostLikedQuery;
import com.nubble.backend.user.feature.UserDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPostDetailFacade {

    private final GetPostWithUserService getPostWithUserService;
    private final IsPostLikedService isPostLikedService;
    private final CategoryService categoryService;
    private final BoardService boardService;
    private final IsPostLikedQueryMapper isPostLikedQueryMapper;

    public GetPostDetailFacadeInfo getPostDetailById(GetPostWithUserQuery query) {
        PostWithUserDto postWithUser = getPostWithUserService.getPostWithUser(query);
        BoardDto board = boardService.getBoardById(postWithUser.post().boardId());
        CategoryDto category = categoryService.getCategoryById(board.categoryId());

        IsPostLikedQuery isPostLikedQuery = isPostLikedQueryMapper.toQuery(query.postId(), query.userId());
        boolean postLiked = isPostLikedService.isPostLiked(isPostLikedQuery);

        return GetPostDetailFacadeInfo.builder()
                .post(postWithUser.post())
                .user(postWithUser.user())
                .board(board)
                .category(category)
                .postLiked(postLiked).build();
    }

    @Builder
    public record GetPostDetailFacadeInfo(
            PostDto post,
            UserDto user,
            BoardDto board,
            CategoryDto category,
            boolean postLiked
    ) {

    }
}
