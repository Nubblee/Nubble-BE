package com.nubble.backend.post.controller.mapper;

import com.nubble.backend.comment.service.member.MemberCommentCommand.CreateCommand;
import com.nubble.backend.post.controller.PostRequest.MemberCommentCreateRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberCommentCommandMapper {


    CreateCommand toCreateCommand(MemberCommentCreateRequest request);
}
