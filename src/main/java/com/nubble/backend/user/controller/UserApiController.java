package com.nubble.backend.user.controller;

import static com.nubble.backend.user.controller.UserResponse.*;

import com.nubble.backend.interceptor.session.SessionRequired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserApiController {

    @SessionRequired
    @GetMapping("/me")
    public ResponseEntity<LoggedInUserGetResponse> getLoggedInUser() {
        return ResponseEntity.ok()
                .body(LoggedInUserGetResponse.builder()
                        .username("user")
                        .nickname("닉네임")
                        .build());
    }
}
