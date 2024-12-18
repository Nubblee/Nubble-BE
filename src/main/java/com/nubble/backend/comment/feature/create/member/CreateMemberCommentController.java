package com.nubble.backend.comment.feature.create.member;

import com.nubble.backend.comment.feature.create.CreateCommentResponse;
import com.nubble.backend.comment.feature.create.member.CreateMemberCommentService.CreateMemberCommentCommand;
import com.nubble.backend.config.interceptor.session.SessionRequired;
import com.nubble.backend.config.resolver.UserSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreateMemberCommentController {

    private final CreateMemberCommentMapper mapper;
    private final CreateMemberCommentService service;

    @PostMapping(
            path = "/posts/{postId:\\d+}/comments/member",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @SessionRequired
    public ResponseEntity<CreateCommentResponse> create(
            @PathVariable Long postId,
            UserSession userSession,
            @Valid @RequestBody CreateMemberCommentRequest request
    ) {
        CreateMemberCommentCommand command = mapper.toCommand(postId, userSession.userId(), request);
        long newCommentId = service.create(command);

        CreateCommentResponse response = new CreateCommentResponse(newCommentId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @Builder
    public record CreateMemberCommentRequest(
            @NotBlank(message = "댓글 내용은 비어있을 수 없습니다.")
            String content
    ) {

    }
}
