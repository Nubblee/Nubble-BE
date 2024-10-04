package com.nubble.backend.file.controller;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileResponse {

    @Builder
    public record FileUploadResponse(
            String fileName,
            String baseUrl
    ) {
    }
}
