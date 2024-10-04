package com.nubble.backend.file.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class FileServiceTest {

    @Autowired
    private FileService fileService;

    @MockBean
    private UUIDGenerator uuidGenerator;

    @MockBean
    private FileUploader fileUploader;

    @DisplayName("새로운 파일명을 만들어 파일을 업로드합니다.")
    @Test
    void uploadFile_success() {
        // given
        FileCommand.FileUploadCommand command = FileCommand.FileUploadCommand.builder()
                .fileName("example.txt")
                .fileData("Hello World".getBytes())
                .build();

        UUID uuid = UUID.randomUUID();
        given(uuidGenerator.generateId())
                .willReturn(uuid);

        String baseUrl = "https://test-bucket.s3.amazonaws.com/";
        given(fileUploader.getBaseUrl()).willReturn(baseUrl);

        // when
        FileInfo fileInfo = fileService.uploadFile(command);

        // then
        assertAll(
                () -> {
                    assertThat(fileInfo.fileName()).isNotEqualTo(command.fileName());
                    assertThat(fileInfo.fileName()).isEqualTo(uuid + ".txt");
                },
                () -> assertThat(fileInfo.baseUrl()).isEqualTo(baseUrl)
        );
    }
}
