package com.nubble.backend.file.service;

public interface FileUploader {

    void upload(String fileName, byte[] file);

    String getBaseUrl();
}
