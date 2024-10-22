package com.nubble.backend.comment.controller;

import com.nubble.backend.comment.controller.CommentRequest.GuestCommentDeleteRequest;
import com.nubble.backend.comment.mapper.CommentCommandMapper;
import com.nubble.backend.comment.service.CommentCommand.CommentDeleteCommand;
import com.nubble.backend.comment.service.CommentService;
import com.nubble.backend.comment.service.CommentType;
import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.interceptor.session.SessionRequired;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentCommandMapper commentCommandMapper;
    private final CommentService commentService;

    @DeleteMapping("/member/{commentId}")
    @SessionRequired
    public ResponseEntity<Void> deleteMemberComment(
            @PathVariable Long commentId, UserSession userSession
    ) {
        CommentDeleteCommand command = commentCommandMapper.toCommentDeleteCommand(commentId, userSession.userId(),
                CommentType.MEMBER);
        commentService.deleteComment(command);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping(
            path = "/guest/{commentId}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteGuestComment(
            @PathVariable Long commentId, @Valid @RequestBody GuestCommentDeleteRequest request
    ) {
        CommentDeleteCommand command = commentCommandMapper.toCommentDeleteCommand(commentId, request,
                CommentType.GUEST);
        commentService.deleteComment(command);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
