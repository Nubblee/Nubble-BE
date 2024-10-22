package com.nubble.backend.post.comment.mapper;

import com.nubble.backend.post.comment.controller.CommentRequest.GuestCommentDeleteRequest;
import com.nubble.backend.post.comment.service.guest.GuestCommentCommand.CreateCommand;
import com.nubble.backend.post.comment.service.guest.GuestCommentCommand.DeleteCommand;
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

    CreateCommand toCreateCommand(PostRequest.GuestCommentCreateRequest request);

    DeleteCommand toDeleteCommand(GuestCommentDeleteRequest request);
}
