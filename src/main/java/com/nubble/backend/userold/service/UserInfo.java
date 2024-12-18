package com.nubble.backend.userold.service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfo {

    @Builder
    public record UserDto(
            String username,
            String nickname
    ) {
    }
}
