package com.nubble.backend.file.service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileCommand {

    @Builder
    public record FileUploadCommand(
            String fileName,
            byte[] fileData
    ) {

    }
}
