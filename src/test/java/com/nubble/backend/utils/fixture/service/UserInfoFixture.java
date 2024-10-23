package com.nubble.backend.utils.fixture.service;

import com.nubble.backend.user.service.UserInfo;
import com.nubble.backend.utils.fixture.domain.UserFixture;

public class UserInfoFixture {
    private UserInfoFixture() {

    }

    public static UserDtoFixture aUserDto() {
        return new UserDtoFixture();
    }

    public static class UserDtoFixture {

        private final UserInfo.UserDto.UserDtoBuilder builder;

        private UserDtoFixture() {
            builder = UserInfo.UserDto.builder()
                    .username(UserFixture.DEFAULT_USERNAME)
                    .nickname(UserFixture.DEFAULT_NICKNAME);
        }

        public UserInfo.UserDto build() {
            return builder.build();
        }
    }
}
