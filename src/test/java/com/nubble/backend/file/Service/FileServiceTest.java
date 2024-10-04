package com.nubble.backend.file.Service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nubble.backend.file.Service.FileService.FileInfo;
import com.nubble.backend.file.Service.FileService.FileUploadCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FileServiceTest {

    @Autowired
    private FileService fileService;

    @DisplayName("새로운 파일명을 만들어 파일을 업로드합니다.")
    @Test
    void uploadFile_success() {
        // given
        FileUploadCommand command = FileUploadCommand.builder()
                .fileName("temp.txt")
                .fileData("Hello World".getBytes())
                .build();

        // when
        FileInfo fileInfo = fileService.uploadFile(command);

        // then
        assertThat(fileInfo.fileName()).isNotEqualTo(command.fileName());
    }
}
