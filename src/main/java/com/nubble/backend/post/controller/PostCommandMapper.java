package com.nubble.backend.post.controller;

import com.nubble.backend.post.controller.PostRequest.PostCreateRequest;
import com.nubble.backend.post.controller.PostRequest.PostPublishRequest;
import com.nubble.backend.post.service.PostCommand.PostCreateCommand;
import com.nubble.backend.post.service.PostCommand.PostPublishCommand;
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

    PostPublishCommand toPostPublishCommand(PostPublishRequest request, Long postId, long userId);
}
