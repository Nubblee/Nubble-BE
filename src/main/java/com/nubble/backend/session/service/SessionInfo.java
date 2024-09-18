package com.nubble.backend.session.service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionInfo {

    @Builder
    public record SessionCreationInfo(
            String userId,
            String cookieName,
            String sessionId,
            long maxAgeSeconds,
            String path,
            String domain,
            boolean secure,
            boolean httpOnly,
            String sameSite
    ) {

    }
}
