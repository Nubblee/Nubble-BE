package com.nubble.backend.post.feature.create;

import com.nubble.backend.config.interceptor.session.SessionRequired;
import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.feature.create.CreatePostService.CreatePostCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreatePostController {

    private final CreatePostMapper mapper;
    private final CreatePostService service;

    @PostMapping(
            path = "/posts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @SessionRequired
    public ResponseEntity<CreatePostResponse> createPost(
            @Valid @RequestBody CreatePostRequest request, UserSession userSession
    ) {
        CreatePostCommand command = mapper.toCommand(request, userSession.userId());
        long newPostId = service.create(command);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreatePostResponse(newPostId));
    }

    @Builder
    public record CreatePostRequest(
            @NotBlank(message = "게시글 제목은 비어있을 수 없습니다.")
            String title,

            @NotBlank(message = "게시글 내용은 비어있을 수 없습니다.")
            String content,

            @NotNull(message = "게시판 아이디는 null일 수 없습니다.")
            Long boardId,

            @NotNull(message = "게시글 상태는 null일 수 없습니다.")
            PostStatus status,

            String thumbnailUrl,

            String description
    ) {

    }

    public record CreatePostResponse(
            long postId
    ) {

    }
}
