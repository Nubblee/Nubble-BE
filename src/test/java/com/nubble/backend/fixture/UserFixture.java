package com.nubble.backend.fixture;

import com.nubble.backend.user.domain.User;

public class UserFixture {
    private static final String DEFAULT_USERNAME = "user";
    private static final String DEFAULT_PASSWORD = "1234";
    private static final String DEFAULT_NICKNAME = "kiki";

    private final User.UserBuilder builder;

    public static UserFixture aUser() {
        return new UserFixture();
    }

    private UserFixture() {
        this.builder = User.builder()
                .username(DEFAULT_USERNAME)
                .password(DEFAULT_PASSWORD)
                .nickname(DEFAULT_NICKNAME);
    }

    public User build() {
        return this.builder.build();
    }

    public UserFixture withUsername(String username) {
        this.builder.username(username);
        return this;
    }

    public UserFixture withPassword(String password) {
        this.builder.password(password);
        return this;
    }

    public UserFixture withNickname(String nickname) {
        this.builder.nickname(nickname);
        return this;
    }
}
