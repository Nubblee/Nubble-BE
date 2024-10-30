package com.nubble.backend.userold.controller;

import static com.nubble.backend.userold.controller.UserResponse.LoggedInUserGetResponse;

import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.config.interceptor.session.SessionRequired;
import com.nubble.backend.userold.service.UserInfo;
import com.nubble.backend.userold.service.UserService;
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
        UserInfo.UserDto userDto = userService.getUser(userSession.userId());

        return ResponseEntity.ok()
                .body(LoggedInUserGetResponse.builder()
                        .username(userDto.username())
                        .nickname(userDto.nickname())
                        .build());
    }
}
