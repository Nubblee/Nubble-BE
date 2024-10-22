package com.nubble.backend.comment.mapper;

import com.nubble.backend.comment.service.guest.GuestCommentCommand;
import com.nubble.backend.post.controller.PostRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface GuestCommentCommandMapper {

    GuestCommentCommand.CreateCommand toCreateCommand(PostRequest.GuestCommentCreateRequest request);
}
