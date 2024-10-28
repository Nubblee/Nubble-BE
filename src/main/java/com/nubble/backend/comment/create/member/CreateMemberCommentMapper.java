package com.nubble.backend.comment.create.member;

import com.nubble.backend.comment.create.member.CreateMemberCommentController.CreateMemberCommentRequest;
import com.nubble.backend.comment.create.member.CreateMemberCommentService.CreateMemberCommentCommand;
import com.nubble.backend.common.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface CreateMemberCommentMapper {

    CreateMemberCommentCommand toCommand(Long postId, Long userId, CreateMemberCommentRequest request);
}
