package com.nubble.backend.userold.session.controller;

import com.nubble.backend.config.interceptor.session.SessionRequired;
import com.nubble.backend.userold.session.controller.SessionRequest.SessionIssueRequest;
import com.nubble.backend.userold.session.controller.SessionResponse.SessionIssueResponse;
import com.nubble.backend.userold.session.service.SessionCommand.SessionCreateCommand;
import com.nubble.backend.userold.session.service.SessionInfo;
import com.nubble.backend.userold.session.service.SessionService;
import jakarta.validation.Valid;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/sessions")
@RequiredArgsConstructor
public class SessionApiController {

    private final SessionService sessionService;
    private final SessionCommandMapper sessionCommandMapper;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SessionIssueResponse> issueSession(@Valid @RequestBody SessionIssueRequest request) {
        SessionCreateCommand command = sessionCommandMapper.fromRequest(request);
        SessionInfo info = sessionService.createSession(command);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SessionIssueResponse.builder()
                        .userId(info.userId())
                        .headerName("SESSION-ID") // todo 전역으로 관리해야 됨
                        .sessionId(info.sessionId())
                        .expirationEpochMs(convertToEpochMilli(info)) // todo 에포크 컨버터 빈을 통해 함수를 사용하도록 수정
                        .build());
    }

    private static long convertToEpochMilli(SessionInfo info) {
        return info.expireAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @SessionRequired
    @GetMapping("/validate")
    public ResponseEntity<Void> validateSession() {
        return ResponseEntity.ok().build();
    }
}
