package com.nubble.backend.comment.feature.delete.guest;

import com.nubble.backend.comment.feature.delete.guest.DeleteGuestCommentController.DeleteGuestCommentRequest;
import com.nubble.backend.comment.feature.delete.guest.DeleteGuestCommentService.DeleteGuestCommentCommand;
import com.nubble.backend.common.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface DeleteGuestCommentMapper {

    DeleteGuestCommentCommand toCommand(Long commentId, DeleteGuestCommentRequest request);
}
