package com.nubble.backend.config.resolver;

import lombok.Builder;

@Builder
public record UserSession(
        long userId,
        String sessionId
) {

}
