package com.nubble.backend.file.service;

import org.springframework.stereotype.Component;

@Component
public class FileExtensionExtractor {

    public String extract(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex == -1) {
            throw new RuntimeException("확장자명이 존재하는 파일만 올릴 수 있습니다.");
        }

        return fileName.substring(lastIndex);
    }
}

