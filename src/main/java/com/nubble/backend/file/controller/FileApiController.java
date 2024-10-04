package com.nubble.backend.file.controller;

import com.nubble.backend.file.service.FileService;
import com.nubble.backend.interceptor.session.SessionRequired;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileApiController {

    private final FileService fileService;

    @SessionRequired
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(FileUploadResponse.builder().build());
    }

    @Builder
    public record FileUploadResponse(
            String fileName,
            String baseUrl
    ) {
    }
}
