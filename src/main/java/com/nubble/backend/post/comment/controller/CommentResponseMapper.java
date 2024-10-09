package com.nubble.backend.post.comment.controller;

import com.nubble.backend.post.comment.controller.CommentResponse.CommentCreateResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CommentResponseMapper {


    CommentCreateResponse toCommentCreateResponse(Long commentId);
}
