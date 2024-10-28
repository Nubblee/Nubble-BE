package com.nubble.backend.comment.create.guest;

import com.nubble.backend.comment.create.CreateCommentResponse;
import jakarta.validation.Valid;
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
public class CreateGuestCommentController {

    private final CreateGuestCommentMapper mapper;
    private final CreateGuestCommentService service;

    @PostMapping(
            path = "posts/{postId:\\d+}/comments/guest",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateCommentResponse> createGuestComment(
            @PathVariable Long postId,
            @Valid @RequestBody CreateGuestCommentRequest request
    ) {
        CreateGuestCommentCommand command = mapper.toCommand(postId, request);
        long newCommentId = service.create(command);

        CreateCommentResponse response = new CreateCommentResponse(newCommentId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
