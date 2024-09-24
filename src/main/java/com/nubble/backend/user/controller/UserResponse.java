package com.nubble.backend.user.controller;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {

    @Builder
    public record LoggedInUserGetResponse(
            String username,
            String nickname
    ) {
    }
}
