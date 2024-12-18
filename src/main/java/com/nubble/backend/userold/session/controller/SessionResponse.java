package com.nubble.backend.userold.session.controller;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionResponse {

    @Builder
    public record SessionIssueResponse(
            Long userId,
            String headerName,
            String sessionId,
            long expirationEpochMs
    ) {

    }
}
