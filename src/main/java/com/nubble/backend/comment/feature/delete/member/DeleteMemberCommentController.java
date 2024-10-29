package com.nubble.backend.comment.feature.delete.member;

import com.nubble.backend.comment.feature.delete.member.DeleteMemberCommentService.DeleteMemberCommentCommand;
import com.nubble.backend.config.interceptor.session.SessionRequired;
import com.nubble.backend.config.resolver.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteMemberCommentController {

    private final DeleteMemberCommentMapper mapper;
    private final DeleteMemberCommentService service;

    @SessionRequired
    @DeleteMapping(path = "/comments/member/{commentId:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Long commentId, UserSession userSession) {
        DeleteMemberCommentCommand command = mapper.toCommand(commentId, userSession.userId());
        service.delete(command);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
