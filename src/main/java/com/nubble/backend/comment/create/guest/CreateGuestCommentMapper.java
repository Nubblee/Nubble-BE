package com.nubble.backend.comment.create.guest;

import com.nubble.backend.comment.create.guest.CreateGuestCommentController.CreateGuestCommentRequest;
import com.nubble.backend.comment.create.guest.CreateGuestCommentService.CreateGuestCommentCommand;
import com.nubble.backend.common.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface CreateGuestCommentMapper {

    CreateGuestCommentCommand toCommand(Long postId, CreateGuestCommentRequest request);
}
