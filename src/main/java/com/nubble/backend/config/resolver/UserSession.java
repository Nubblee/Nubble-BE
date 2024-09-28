package com.nubble.backend.config.resolver;

import lombok.Builder;

@Builder
public record UserSession(
        long userId, // todo 호환성을 위해 Long으로 수정
        String sessionId
) {

}
