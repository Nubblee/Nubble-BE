package com.nubble.backend.session.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionCommand {

    public record SessionCreationCommand(
            String userName,
            String password
    ) {

    }
}
