package com.nubble.backend.file.Service;

import java.util.UUID;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileExtensionExtractor fileExtensionExtractor;
    private final UUIDGenerator uuidGenerator;
    private final FileUploader fileUploader;

    public FileInfo uploadFile(FileUploadCommand command) {
        // 확장자를 추출합니다.
        String extensionName = fileExtensionExtractor.extract(command.fileName());

        // UUID를 이용해서 새로운 파일명을 만듭니다.
        String uuid = uuidGenerator.generateId().toString();

        // 새로운 파일명 + 확장자를 합쳐서 새로운 파일명을 만듭니다.
        String newFileName = uuid + extensionName;

        // fileUploader에게 fileupload를 요청합니다.
        fileUploader.upload(newFileName, command.fileData());
        return FileInfo.builder()
                .fileName(newFileName)
                .baseUrl(fileUploader.getBaseUrl())
                .build();
    }

    @Component
    public static class FileExtensionExtractor {

        public String extract(String fileName) {
            int lastIndex = fileName.lastIndexOf(".");
            if (lastIndex == -1) {
                throw new RuntimeException("확장자명이 존재하는 파일만 올릴 수 있습니다.");
            }

            return fileName.substring(lastIndex);
        }
    }

    @Component
    public static class UUIDGenerator {

        public UUID generateId() {
            return UUID.randomUUID();
        }
    }

    public interface FileUploader {

        void upload(String fileName, byte[] file);

        String getBaseUrl();
    }

    @Component
    public static class S3FileUploader implements FileUploader {

        @Override
        public void upload(String fileName, byte[] file) {

        }

        @Override
        public String getBaseUrl() {
            return "https://example.com";
        }
    }

    @Builder
    public record FileUploadCommand(
            String fileName,
            byte[] fileData
    ) {

    }

    @Builder
    public record FileInfo(
            String fileName,
            String baseUrl
    ) {

    }
}
