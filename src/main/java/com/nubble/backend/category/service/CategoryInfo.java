package com.nubble.backend.category.service;

import lombok.Builder;

@Builder
public record CategoryInfo(
        long id,
        String name
) {

}
