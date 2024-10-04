package com.nubble.backend.file.controller;

import com.nubble.backend.file.service.FileCommand.FileUploadCommand;
import java.io.IOException;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.web.multipart.MultipartFile;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface FileCommandMapper {

    @Mapping(target = "fileName", source = "originalFilename")
    @Mapping(target = "fileData", expression = "java(getFileData(file))")
    FileUploadCommand toFileUploadCommand(MultipartFile file);

    default byte[] getFileData(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("파일 데이터 읽기에 실패했습니다.", e);
        }
    }
}
