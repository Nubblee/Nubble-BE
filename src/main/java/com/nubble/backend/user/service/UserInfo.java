package com.nubble.backend.user.service;

import lombok.Builder;

@Builder
public record UserInfo(
        String username,
        String nickname
) {

}
