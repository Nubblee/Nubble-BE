package com.nubble.backend.session.service;

import com.nubble.backend.session.domain.Session;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.SessionBuilder;

@Builder
public record SessionInfo(
        Long userId,
        String sessionId
) {

    public static SessionInfo fromDomain(Session session) {
        return SessionInfo.builder()
                .userId(session.getUser().getId())
                .sessionId(session.getAccessId())
                .build();
    }
}
