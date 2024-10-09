package com.nubble.backend.post.comment.controller;

import com.nubble.backend.post.comment.controller.CommentRequest.GuestCommentCreateRequest;
import com.nubble.backend.post.comment.controller.CommentRequest.MemberCommentCreateRequest;
import com.nubble.backend.post.comment.service.CommentCommand.CommentCreateCommand;
import com.nubble.backend.post.comment.service.CommentType;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface CommentCommandMapper {

    CommentCreateCommand toCommentCreateCommand(MemberCommentCreateRequest request, Long postId, Long userId, CommentType type);

    CommentCreateCommand toCommentCreateCommand(GuestCommentCreateRequest request, Long postId);
}
