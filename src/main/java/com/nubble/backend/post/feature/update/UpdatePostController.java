package com.nubble.backend.post.feature.update;

import com.nubble.backend.config.interceptor.session.SessionRequired;
import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.feature.update.UpdatePostService.UpdatePostCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdatePostController {

    private final UpdatePostMapper mapper;
    private final UpdatePostService service;

    @SessionRequired
    @PutMapping(
            path = "/posts/{postId:\\d+}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(
            @Valid @RequestBody UpdatePostRequest request,
            @PathVariable Long postId,
            UserSession userSession
    ) {
        UpdatePostCommand command = mapper.toCommand(request, postId, userSession.userId());
        service.update(command);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Builder
    public record UpdatePostRequest(
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
}
