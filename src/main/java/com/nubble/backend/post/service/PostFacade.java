package com.nubble.backend.post.service;

import com.nubble.backend.category.board.service.BoardInfo.BoardDto;
import com.nubble.backend.category.board.service.BoardService;
import com.nubble.backend.category.service.CategoryInfo.CategoryDto;
import com.nubble.backend.category.service.CategoryService;
import com.nubble.backend.post.service.PostInfo.PostWithCategoryDto;
import com.nubble.backend.post.service.PostInfo.PostWithUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostFacade {

    private final PostService postService;
    private final BoardService boardService;
    private final CategoryService categoryService;

    public PostWithCategoryDto getPostById(long postId) {
        PostWithUserDto postWithUserDto = postService.getPostById(postId);
        BoardDto boardDto = boardService.getBoardById(postWithUserDto.post().boardId());
        CategoryDto category = categoryService.getCategoryById(boardDto.categoryId());

        return PostWithCategoryDto.builder()
                .postWithUserDto(postWithUserDto)
                .board(boardDto)
                .category(category)
                .build();
    }
}
