package com.nubble.backend.comment.feature.findallbypost;

import com.nubble.backend.comment.domain.Comment;
import com.nubble.backend.comment.domain.guest.GuestComment;
import com.nubble.backend.comment.domain.member.MemberComment;
import com.nubble.backend.comment.feature.findallbypost.FindAllByPostCommentController.FindByPostCommentResponse;
import com.nubble.backend.common.BaseMapperConfig;
import com.nubble.backend.common.exception.UnsupportedTypeException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface FindAllByPostCommentMapper {

    default FindByPostCommentResponse toResponse(Comment comment) {
        if (comment instanceof MemberComment memberComment) {
            return toResponse(memberComment);
        } else if (comment instanceof GuestComment guestComment) {
            return toResponse(guestComment);
        }
        throw new UnsupportedTypeException("지원하지 않는 댓글 유형입니다: " + comment.getClass().getName());
    }

    @Mapping(target = "commentId", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.nickname")
    @Mapping(target = "type", source = "commentType")
    @Mapping(target = "guestName", ignore = true)
    FindByPostCommentResponse toResponse(MemberComment memberComment);

    @Mapping(target = "commentId", source = "id")
    @Mapping(target = "type", source = "commentType")
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "userName", ignore = true)
    FindByPostCommentResponse toResponse(GuestComment guestComment);
}
