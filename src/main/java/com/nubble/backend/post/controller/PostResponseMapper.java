package com.nubble.backend.post.controller;

import com.nubble.backend.post.controller.PostResponse.PostCreateResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface PostResponseMapper {

    PostCreateResponse toPostCreateResponse(Long postId);

    @Mapping(target = "memberCommentId", source = "newCommentId")
    PostResponse.MemberCommentCreateResponse toMemberCommentCreateResponse(Long newCommentId);
}
