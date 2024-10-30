package com.nubble.backend.user.feature;

import com.nubble.backend.userold.domain.User;
import lombok.Builder;

@Builder
public record UserDto(
        Long id,
        String username,
        String nickname
) {

    public static UserDto fromDomain(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname()).build();
    }
}
