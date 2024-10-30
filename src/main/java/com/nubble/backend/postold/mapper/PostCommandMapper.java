package com.nubble.backend.postold.mapper;

import com.nubble.backend.postold.controller.PostRequest.PostUpdateRequest;
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

    PostUpdateCommand toPostUpdateCommand(PostUpdateRequest request, Long postId, long userId);
}
