package com.nubble.backend.session.service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionCommand {

    @Builder
    public record SessionCreateCommand(
            String username,
            String password
    ) {

    }
}
