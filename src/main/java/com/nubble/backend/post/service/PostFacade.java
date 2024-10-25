package com.nubble.backend.post.service;

import com.nubble.backend.category.board.service.BoardService;
import com.nubble.backend.category.service.CategoryService;
import com.nubble.backend.post.service.PostInfo.PostWithCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostFacade {

    private final PostService postService;
    private final BoardService boardService;
    private final CategoryService categoryService;

    public PostWithCategoryDto getPostById(long postId) {
        return null;
    }
}
