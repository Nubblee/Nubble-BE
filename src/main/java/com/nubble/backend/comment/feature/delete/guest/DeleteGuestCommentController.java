package com.nubble.backend.comment.feature.delete.guest;

import com.nubble.backend.comment.feature.delete.guest.DeleteGuestCommentService.DeleteGuestCommentCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteGuestCommentController {

    private final DeleteGuestCommentMapper mapper;
    private final DeleteGuestCommentService service;

    @DeleteMapping(
            path = "/comments/guest/{commentId:\\d+}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(
            @PathVariable Long commentId,
            @Valid @RequestBody DeleteGuestCommentRequest request
    ) {
        DeleteGuestCommentCommand command = mapper.toCommand(commentId, request);
        service.delete(command);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Builder
    public record DeleteGuestCommentRequest(
            @NotBlank
            String guestPassword
    ) {

    }
}
