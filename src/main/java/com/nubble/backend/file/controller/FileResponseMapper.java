package com.nubble.backend.file.controller;

import com.nubble.backend.file.controller.FileResponse.FileUploadResponse;
import com.nubble.backend.file.service.FileInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface FileResponseMapper {

    FileUploadResponse toFileUploadResponse(FileInfo info);
}
