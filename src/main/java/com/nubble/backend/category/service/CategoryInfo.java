package com.nubble.backend.category.service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryInfo {

    @Builder
    public record CategoryDto(
            long id,
            String name
    ) {
    }

}
