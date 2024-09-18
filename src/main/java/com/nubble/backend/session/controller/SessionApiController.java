package com.nubble.backend.session.controller;

import com.nubble.backend.session.controller.SessionRequest.SessionIssuanceRequest;
import com.nubble.backend.session.service.SessionCommand.SessionCreationCommand;
import com.nubble.backend.session.service.SessionInfo.SessionCreationInfo;
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
    public ResponseEntity<Void> issue(@RequestBody SessionIssuanceRequest request) {
        SessionCreationCommand command = sessionCommandMapper.fromRequest(request);
        SessionCreationInfo sessionCreationInfo = sessionService.create(command);

        ResponseCookie sessionCookie = generateSessionCookie(sessionCreationInfo);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, sessionCookie.toString())
                .build();
    }

    private static ResponseCookie generateSessionCookie(SessionCreationInfo sessionCreationInfo) {
        return ResponseCookie.from(sessionCreationInfo.cookieName())
                .value(sessionCreationInfo.sessionId())
                .maxAge(sessionCreationInfo.maxAgeSeconds())
                .path(sessionCreationInfo.path())
                .httpOnly(sessionCreationInfo.httpOnly())
                .build();
    }
}
