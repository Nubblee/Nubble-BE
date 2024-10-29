package com.nubble.backend.postold.mapper;

import com.nubble.backend.postold.controller.PostRequest.PostCreateRequest;
import com.nubble.backend.postold.controller.PostRequest.PostUpdateRequest;
import com.nubble.backend.postold.service.PostCommand.PostCreateCommand;
import com.nubble.backend.postold.service.PostCommand.PostUpdateCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface PostCommandMapper {

    PostCreateCommand toPostCreateCommand(PostCreateRequest request, long userId);

    PostUpdateCommand toPostUpdateCommand(PostUpdateRequest request, Long postId, long userId);
}
