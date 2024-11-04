package com.nubble.backend.post.feature.findallbyboard;

import com.nubble.backend.post.feature.PostWithUserDto;
import java.time.LocalDateTime;
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
public class FindAllPostsByBoardController {

    private final FindAllPostsByBoardService findAllPostsByBoardService;
    private final FindAllPostsByBoardMapper mapper;

    @GetMapping(
            path = "/boards/{boardId}",
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FindAllPostsByBoardResponse> findAllByBoardId(@PathVariable Long boardId) {
        List<PostWithUserDto> dtos = findAllPostsByBoardService.findAllByBoardId(boardId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(mapper.toResponse(dtos));
    }

    public record FindAllPostsByBoardResponse(
            List<FindPostByBoardResponse> posts
    ) {
    }

    public record FindPostByBoardResponse(
            long id,
            String title,
            String thumbnailUrl,
            String description,
            String username,
            LocalDateTime createdAt,
            int likeCount
    ) {
    }
}
