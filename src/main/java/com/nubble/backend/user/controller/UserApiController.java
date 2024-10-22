package com.nubble.backend.user.controller;

import static com.nubble.backend.user.controller.UserResponse.LoggedInUserGetResponse;

import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.config.interceptor.session.SessionRequired;
import com.nubble.backend.user.service.UserInfo;
import com.nubble.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @SessionRequired
    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoggedInUserGetResponse> getLoggedInUser(UserSession userSession) {
        UserInfo info = userService.getUser(userSession.userId());

        return ResponseEntity.ok()
                .body(LoggedInUserGetResponse.builder()
                        .username(info.username())
                        .nickname(info.nickname())
                        .build());
    }
}
