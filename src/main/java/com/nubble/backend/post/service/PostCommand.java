package com.nubble.backend.post.service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCommand {

    @Builder
    public record PostCreateCommand(
            String title,
            String content
    ) {

    }
}
