package com.nubble.backend.session.controller;

import java.time.Duration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> issue(SessionRequest.Issuance request) {
        ResponseCookie sessionCookie = generateSessionCookie("550e8400-e29b-41d4-a716-446655440000");
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, sessionCookie.toString())
                .build();
    }

    private static ResponseCookie generateSessionCookie(String value) {
        return ResponseCookie.from("SESSION")
                .value(value)
                .maxAge(Duration.ofDays(30))
                .path("/")
                .httpOnly(true)
                .build();
    }
}
