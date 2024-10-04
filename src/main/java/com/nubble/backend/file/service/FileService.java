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
}
