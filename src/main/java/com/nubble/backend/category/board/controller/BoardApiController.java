package com.nubble.backend.category.board.controller;

import com.nubble.backend.post.service.PostInfo;
import com.nubble.backend.post.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardApiController {

    private final PostService postService;
    private final BoardResponseMapper boardResponseMapper;

    @GetMapping(
            path = "/{boardId}",
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BoardResponse.PostsWithUserResponse> findByBoardId(@PathVariable Long boardId) {
        List<PostInfo.PostWithUserDto> postsWithUser = postService.findPostsByBoardId(boardId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(boardResponseMapper.toPostsWithUserResponse(postsWithUser));
    }
}
