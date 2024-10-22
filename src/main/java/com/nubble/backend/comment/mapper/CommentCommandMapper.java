package com.nubble.backend.comment.mapper;

import com.nubble.backend.comment.controller.CommentRequest.GuestCommentDeleteRequest;
import com.nubble.backend.comment.service.CommentCommand.CommentDeleteCommand;
import com.nubble.backend.comment.service.CommentType;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface CommentCommandMapper {
    CommentDeleteCommand toCommentDeleteCommand(Long commentId, Long userId, CommentType member);

    CommentDeleteCommand toCommentDeleteCommand(Long commentId, GuestCommentDeleteRequest request, CommentType guest);
}
