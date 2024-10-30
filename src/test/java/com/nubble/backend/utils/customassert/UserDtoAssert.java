package com.nubble.backend.utils.customassert;

import com.nubble.backend.userold.service.UserInfo;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class UserDtoAssert extends AbstractAssert<UserDtoAssert, UserInfo.UserDto> {

    private UserDtoAssert(UserInfo.UserDto actual) {
        super(actual, UserDtoAssert.class);
        isNotNull();
    }

    public static UserDtoAssert assertThat(UserInfo.UserDto actual) {
        return new UserDtoAssert(actual);
    }

    public void isEqualTo(UserInfo.UserDto expected) {
        assertThat(expected).isNotNull();
        Assertions.assertThat(actual).usingRecursiveComparison()
                .withStrictTypeChecking().isEqualTo(expected);
    }
}
