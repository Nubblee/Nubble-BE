package com.nubble.backend.file.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.file.service.FileCommand.FileUploadCommand;
import com.nubble.backend.file.service.FileInfo;
import com.nubble.backend.file.service.FileService;
import com.nubble.backend.session.service.SessionService;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
class FileApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FileService fileService;

    @MockBean
    private SessionService sessionService;

    @DisplayName("로그인된 유저가 파일을 업로드합니다.")
    @Test
    void uploadFile_success() throws Exception {
        // given
        byte[] content = "Hello, World!".getBytes();
        String fileName = "test.txt";
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                fileName,
                MediaType.TEXT_PLAIN_VALUE,
                content
        );

        FileUploadCommand command = FileUploadCommand.builder()
                .fileName(fileName)
                .fileData(content)
                .build();

        String uuid = UUID.randomUUID().toString();
        String baseUrl = "https://example.com";
        FileInfo info = FileInfo.builder()
                .fileName(uuid)
                .baseUrl(baseUrl)
                .build();
        given(fileService.uploadFile(command))
                .willReturn(info);

        MockHttpServletRequestBuilder requestBuilder = multipart("/files")
                .file(mockFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE);

        // when & then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fileName").value(info.fileName()))
                .andExpect(jsonPath("$.baseUrl").value(baseUrl))
                .andDo(print());
    }
}
