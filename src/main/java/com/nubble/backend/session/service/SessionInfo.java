package com.nubble.backend.session.service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionInfo {

    @Builder
    public record SessionCreateInfo(
            Long userId,
            String sessionId
    ) {

    }
}
