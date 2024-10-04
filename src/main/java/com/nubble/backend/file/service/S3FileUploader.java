package com.nubble.backend.file.service;

import org.springframework.stereotype.Component;

@Component
public class S3FileUploader implements FileUploader {

    @Override
    public void upload(String fileName, byte[] file) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getBaseUrl() {
        return "https://example.com";
    }
}
