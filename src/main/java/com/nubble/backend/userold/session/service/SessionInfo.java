package com.nubble.backend.userold.session.service;

import com.nubble.backend.userold.session.domain.Session;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record SessionInfo(
        Long userId,
        String sessionId,
        LocalDateTime expireAt
        ) {

    public static SessionInfo fromDomain(Session session) {
        return SessionInfo.builder()
                .userId(session.getUser().getId())
                .sessionId(session.getAccessId())
                .expireAt(session.getExpireAt())
                .build();
    }
}
