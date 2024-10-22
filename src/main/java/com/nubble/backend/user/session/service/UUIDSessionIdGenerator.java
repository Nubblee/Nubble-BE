package com.nubble.backend.user.session.service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UUIDSessionIdGenerator extends AbstractSessionIdGenerator {

    public UUIDSessionIdGenerator(
            SessionRepository sessionRepository,
            @Value("${session.id.generation.max-attempts:3}") int maxAttempts) {
        super(sessionRepository, maxAttempts);
    }

    @Override
    protected String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
