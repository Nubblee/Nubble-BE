package com.nubble.backend.post.feature.findpopularposts;

import com.nubble.backend.post.feature.PostWithUserDto;
import com.nubble.backend.post.feature.findallbyboard.FindAllPostsByBoardController.FindAllPostsByBoardResponse;
import com.nubble.backend.post.feature.findallbyboard.FindAllPostsByBoardMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FindPopularPostsController {

    private final FindPopularPostsService service;
    private final FindAllPostsByBoardMapper mapper;

    @GetMapping(
            path = "/boards/{boardId:\\d+}/posts/popular",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FindAllPostsByBoardResponse> findPopularPosts(@PathVariable long boardId) {
        List<PostWithUserDto> dtos = service.findPopularPosts(boardId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(mapper.toResponse(dtos));
    }
}
