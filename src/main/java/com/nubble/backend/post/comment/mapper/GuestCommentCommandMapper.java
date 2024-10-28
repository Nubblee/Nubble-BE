package com.nubble.backend.post.comment.mapper;

import com.nubble.backend.post.comment.controller.CommentRequest.GuestCommentDeleteRequest;
import com.nubble.backend.post.comment.service.guest.GuestCommentCommand.DeleteCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface GuestCommentCommandMapper {


    DeleteCommand toDeleteCommand(GuestCommentDeleteRequest request);
}
