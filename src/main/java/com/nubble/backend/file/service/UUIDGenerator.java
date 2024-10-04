package com.nubble.backend.file.service;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UUIDGenerator {

    public UUID generateId() {
        return UUID.randomUUID();
    }
}
