package com.nubble.backend.user.service;

import lombok.Builder;

public class UserQuery {

    @Builder
    public record ByIdQuery(
            long id
    ) {
    }
}
