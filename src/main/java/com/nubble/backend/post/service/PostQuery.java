package com.nubble.backend.post.service;

import lombok.Builder;

public class PostQuery {

    @Builder
    public record ByIdQuery(
            long id
    ) {

    }
}
