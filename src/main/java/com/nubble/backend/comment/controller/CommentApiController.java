package com.nubble.backend.comment.controller;

import com.nubble.backend.comment.controller.CommentRequest.GuestCommentDeleteRequest;
import com.nubble.backend.comment.mapper.CommentQueryMapper;
import com.nubble.backend.comment.mapper.GuestCommentCommandMapper;
import com.nubble.backend.comment.mapper.MemberCommentCommandMapper;
import com.nubble.backend.comment.service.CommentQuery.CommentByIdQuery;
import com.nubble.backend.comment.service.guest.GuestCommentCommand;
import com.nubble.backend.comment.service.guest.GuestCommentCommandService;
import com.nubble.backend.comment.service.member.MemberCommentCommand.DeleteCommand;
import com.nubble.backend.comment.service.member.MemberCommentCommandService;
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

    private final MemberCommentCommandService memberCommentCommandService;
    private final GuestCommentCommandService guestCommentCommandService;
    private final CommentQueryMapper commentQueryMapper;
    private final MemberCommentCommandMapper memberCommentCommandMapper;
    private final GuestCommentCommandMapper guestCommentCommandMapper;

    @DeleteMapping("/member/{commentId}")
    @SessionRequired
    public ResponseEntity<Void> deleteMemberComment(
            @PathVariable Long commentId, UserSession userSession
    ) {
        CommentByIdQuery commentQuery = commentQueryMapper.toCommentByIdQuery(commentId);
        DeleteCommand command = memberCommentCommandMapper.toDeleteCommand(commentId);
        memberCommentCommandService.delete(commentQuery, command);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping(
            path = "/guest/{commentId}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteGuestComment(
            @PathVariable Long commentId, @Valid @RequestBody GuestCommentDeleteRequest request
    ) {
        CommentByIdQuery commentQuery = commentQueryMapper.toCommentByIdQuery(commentId);
        GuestCommentCommand.DeleteCommand command = guestCommentCommandMapper.toDeleteCommand(request);
        guestCommentCommandService.delete(commentQuery, command);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
