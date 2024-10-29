package com.nubble.backend.comment.feature.findallbypost;

import com.nubble.backend.comment.repository.CommentRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FindAllByPostCommentController {

    private final CommentRepository commentRepository;
    private final FindAllByPostCommentMapper mapper;

    @GetMapping(
            path = "/posts/{postId:\\d+}/comments",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FindAllByPostCommentResponse> findAllByPostId(@PathVariable Long postId) {
        List<FindByPostCommentResponse> list = commentRepository.findAllByPostId(postId).stream()
                .map(mapper::toResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new FindAllByPostCommentResponse(list));
    }

    public record FindAllByPostCommentResponse(
            List<FindByPostCommentResponse> comments
    ) {

    }

    @Builder
    public record FindByPostCommentResponse(
            Long commentId,
            String content,
            LocalDateTime createdAt,
            Long userId,
            String userName,
            String guestName,
            String type
    ) {

    }
}
