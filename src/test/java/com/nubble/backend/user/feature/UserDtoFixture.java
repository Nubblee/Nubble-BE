package com.nubble.backend.user.feature;

import com.nubble.backend.utils.fixture.domain.UserFixture;

public class UserDtoFixture {

    private Long id;
    private String username;
    private String nickname;

    public static UserDtoFixture aUserDto() {
        UserDtoFixture fixture = new UserDtoFixture();
        fixture.id = 31L;
        fixture.nickname = UserFixture.DEFAULT_NICKNAME;
        fixture.username = UserFixture.DEFAULT_USERNAME;

        return fixture;
    }

    private final UserDto.UserDtoBuilder builder;

    private UserDtoFixture() {
        builder = UserDto.builder();
    }

    public UserDto build() {
        return builder.id(id)
                .nickname(nickname)
                .username(username).build();
    }
}
