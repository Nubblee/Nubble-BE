package com.nubble.backend.utils.fixture.domain;

import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.session.domain.Session;
import java.time.LocalDateTime;
import java.util.UUID;

public class AuthSessionFixture {

    public static final String DEFAULT_ACCESS_ID = UUID.randomUUID().toString();
    public static final LocalDateTime DEFAULT_EXPIRE_AT = LocalDateTime.now().plusDays(1);

    private final Session.SessionBuilder builder;

    public static AuthSessionFixture aAuthSession() {
        return new AuthSessionFixture();
    }

    private AuthSessionFixture() {
        builder = Session.builder()
                .accessId(DEFAULT_ACCESS_ID)
                .expireAt(DEFAULT_EXPIRE_AT);
    }

    public Session build() {
        return builder.build();
    }

    public AuthSessionFixture withUser(User user) {
        builder.user(user);
        return this;
    }
}
