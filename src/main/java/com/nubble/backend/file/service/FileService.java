package com.nubble.backend.file.service;

import com.nubble.backend.file.service.FileCommand.FileUploadCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileExtensionExtractor fileExtensionExtractor;
    private final UUIDGenerator uuidGenerator;
    private final FileUploader fileUploader;

    public FileInfo uploadFile(FileUploadCommand command) {
        String extensionName = fileExtensionExtractor.extract(command.fileName());

        String uuid = uuidGenerator.generateId().toString();

        String newFileName = uuid + extensionName;

        fileUploader.upload(newFileName, command.fileData());
        return FileInfo.builder()
                .fileName(newFileName)
                .baseUrl(fileUploader.getBaseUrl())
                .build();
    }
}
