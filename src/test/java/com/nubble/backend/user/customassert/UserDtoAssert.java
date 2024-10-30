package com.nubble.backend.user.customassert;

import com.nubble.backend.user.feature.UserDto;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class UserDtoAssert extends AbstractAssert<UserDtoAssert, UserDto> {

    public UserDtoAssert(UserDto actual) {
        super(actual, UserDtoAssert.class);
        isNotNull();
    }

    public static UserDtoAssert assertThat(UserDto actual) {
        return new UserDtoAssert(actual);
    }

    public void isEqualTo(UserDto expected) {
        assertThat(expected).isNotNull();
        Assertions.assertThat(actual).usingRecursiveComparison()
                .withStrictTypeChecking().isEqualTo(expected);
    }
}
