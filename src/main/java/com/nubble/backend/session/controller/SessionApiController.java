package com.nubble.backend.session.controller;

import com.nubble.backend.session.controller.SessionRequest.SessionIssueRequest;
import com.nubble.backend.session.service.SessionCommand.SessionCreateCommand;
import com.nubble.backend.session.service.SessionInfo.SessionCreateInfo;
import com.nubble.backend.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class SessionApiController {

    private final SessionService sessionService;
    private final SessionCommandMapper sessionCommandMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> issueSession(@RequestBody SessionIssueRequest request) {
        SessionCreateCommand command = sessionCommandMapper.fromRequest(request);
        SessionCreateInfo info = sessionService.createSession(command);

        ResponseCookie sessionCookie = generateSessionCookie(info);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, sessionCookie.toString())
                .build();
    }

    private static ResponseCookie generateSessionCookie(SessionCreateInfo info) {
        return ResponseCookie.from(info.cookieName())
                .value(info.sessionId())
                .maxAge(info.maxAgeSeconds())
                .path(info.path())
                .httpOnly(info.httpOnly())
                .build();
    }
}
