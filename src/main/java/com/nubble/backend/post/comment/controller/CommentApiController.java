package com.nubble.backend.post.comment.controller;

import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.interceptor.session.SessionRequired;
import com.nubble.backend.post.comment.controller.CommentRequest.GuestCommentCreateRequest;
import com.nubble.backend.post.comment.controller.CommentRequest.MemberCommentCreateRequest;
import com.nubble.backend.post.comment.controller.CommentResponse.CommentCreateResponse;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.post.comment.service.CommentService;
import com.nubble.backend.post.comment.service.CommentType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

// todo 해당 컨트롤러의 테스트 코드 만들어야 함
@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentCommandMapper commentCommandMapper;
    private final CommentService commentService;
    private final CommentResponseMapper commentResponseMapper;

    @PostMapping(
            path = "/{postId}/member-comments",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @SessionRequired
    public ResponseEntity<CommentCreateResponse> createMemberComment(
            @Valid @RequestBody MemberCommentCreateRequest request,
            @PathVariable Long postId,
            UserSession userSession
    ) {
        CommentCreateCommand command = commentCommandMapper.toCommentCreateCommand(
                request, postId, userSession.userId(), CommentType.MEMBER);
        long commentId = commentService.createComment(command);

        return ResponseEntity.status(HttpStatus.OK)
                .body(commentResponseMapper.toCommentCreateResponse(commentId));
    }

    @PostMapping(
            path = "/{postId}/guest-comments",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentCreateResponse> createMemberComment(
            @Valid @RequestBody GuestCommentCreateRequest request,
            @PathVariable Long postId
    ) {
        CommentCreateCommand command = commentCommandMapper.toCommentCreateCommand(request, postId);
        long commentId = commentService.createComment(command);

        return ResponseEntity.status(HttpStatus.OK)
                .body(commentResponseMapper.toCommentCreateResponse(commentId));
    }

}
