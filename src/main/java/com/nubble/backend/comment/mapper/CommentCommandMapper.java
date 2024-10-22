package com.nubble.backend.comment.mapper;

import com.nubble.backend.comment.controller.CommentRequest.GuestCommentDeleteRequest;
import com.nubble.backend.comment.controller.CommentRequest.MemberCommentCreateRequest;
import com.nubble.backend.comment.service.CommentCommand.CommentCreateCommand;
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

    // todo builder 패턴을 이용하여 인자 받기, CommentCreateCommand를 Member, Guest로 분리하기
    CommentCreateCommand toCommentCreateCommand(MemberCommentCreateRequest request, Long postId, Long userId,
            CommentType type);

    CommentDeleteCommand toCommentDeleteCommand(Long commentId, Long userId, CommentType member);

    CommentDeleteCommand toCommentDeleteCommand(Long commentId, GuestCommentDeleteRequest request, CommentType guest);
}
