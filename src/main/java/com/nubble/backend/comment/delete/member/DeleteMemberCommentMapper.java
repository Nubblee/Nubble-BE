package com.nubble.backend.comment.delete.member;

import com.nubble.backend.comment.delete.member.DeleteMemberCommentService.DeleteMemberCommentCommand;
import com.nubble.backend.common.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface DeleteMemberCommentMapper {

     DeleteMemberCommentCommand toCommand(Long commentId, Long userId);
}
