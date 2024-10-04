package com.nubble.backend.file.service;

import lombok.Builder;

@Builder
public record FileInfo(
        String fileName,
        String baseUrl
) {

}
