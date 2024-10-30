package com.nubble.backend.post.feature;

import com.nubble.backend.user.feature.UserDto;
import lombok.Builder;

@Builder
public record PostWithUserDto(
        PostDto post,
        UserDto user
) {

}
