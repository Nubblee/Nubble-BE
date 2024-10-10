package com.nubble.backend.post.comment.controller;

import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.interceptor.session.SessionRequired;
import com.nubble.backend.post.comment.controller.CommentRequest.GuestCommentCreateRequest;
import com.nubble.backend.post.comment.controller.CommentRequest.GuestCommentDeleteRequest;
import com.nubble.backend.post.comment.controller.CommentRequest.MemberCommentCreateRequest;
import com.nubble.backend.post.comment.controller.CommentResponse.CommentCreateResponse;
import com.nubble.backend.post.comment.controller.CommentResponse.CommentFindResponses;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.post.comment.service.CommentCommand.CommentDeleteCommand;
import com.nubble.backend.post.comment.service.CommentInfo;
import com.nubble.backend.post.comment.service.CommentService;
import com.nubble.backend.post.comment.service.CommentType;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentCommandMapper commentCommandMapper;
    private final CommentService commentService;
    private final CommentResponseMapper commentResponseMapper;

    @PostMapping(
            path = "/member",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @SessionRequired
    public ResponseEntity<CommentCreateResponse> createMemberComment(
            @PathVariable Long postId,
            UserSession userSession,
            @Valid @RequestBody MemberCommentCreateRequest request
    ) {
        CommentCreateCommand command = commentCommandMapper.toCommentCreateCommand(
                request, postId, userSession.userId(), CommentType.MEMBER);
        long commentId = commentService.createComment(command);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentResponseMapper.toCommentCreateResponse(commentId));
    }

    @PostMapping(
            path = "/guest",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentCreateResponse> createGuestComment(
            @PathVariable Long postId,
            @Valid @RequestBody GuestCommentCreateRequest request
    ) {
        CommentCreateCommand command = commentCommandMapper.toCommentCreateCommand(request, postId);
        long commentId = commentService.createComment(command);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentResponseMapper.toCommentCreateResponse(commentId));
    }

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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentFindResponses> findAllCommentsByPostId(@PathVariable Long postId) {
        List<CommentInfo> commentInfos = commentService.findAllByPostId(postId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(commentResponseMapper.toCommentFindResponses(commentInfos));
    }
}
