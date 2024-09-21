package com.nubble.backend.session.controller;

import com.nubble.backend.config.properties.SessionCookieProperties;
import com.nubble.backend.interceptor.session.SessionRequired;
import com.nubble.backend.session.controller.SessionRequest.SessionIssueRequest;
import com.nubble.backend.session.service.SessionCommand.SessionCreateCommand;
import com.nubble.backend.session.service.SessionInfo;
import com.nubble.backend.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final SessionCookieProperties sessionCookieProperties;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> issueSession(@RequestBody SessionIssueRequest request) {
        SessionCreateCommand command = sessionCommandMapper.fromRequest(request);
        SessionInfo info = sessionService.createSession(command);

        ResponseCookie sessionCookie = ResponseCookie.from(sessionCookieProperties.getName())
                .value(info.sessionId())
                .maxAge(sessionCookieProperties.getMaxAge())
                .path(sessionCookieProperties.getPath())
                .httpOnly(sessionCookieProperties.isHttpOnly())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, sessionCookie.toString())
                .build();
    }

    @SessionRequired
    @GetMapping("/validate")
    public ResponseEntity<Void> validateSession() {
        return ResponseEntity.ok().build();
    }
}
