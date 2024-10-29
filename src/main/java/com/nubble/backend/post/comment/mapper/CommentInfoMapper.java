package com.nubble.backend.post.comment.mapper;

import com.nubble.backend.comment.domain.Comment;
import com.nubble.backend.comment.domain.guest.GuestComment;
import com.nubble.backend.comment.domain.member.MemberComment;
import com.nubble.backend.comment.domain.CommentInfo.CommentDto;
import com.nubble.backend.comment.domain.CommentInfo.CommentDto.CommentDtoBuilder;
import com.nubble.backend.post.comment.service.CommentTypeDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CommentInfoMapper {


    default CommentDto toCommentDto(Comment comment) {
        CommentDtoBuilder builder = CommentDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt());

        if (comment instanceof MemberComment memberComment) {
            builder.userId(memberComment.getUser().getId())
                    .userName(memberComment.getUser().getNickname())
                    .type(CommentTypeDto.MEMBER);
        } else if (comment instanceof GuestComment guestComment) {
            builder.guestName(guestComment.getGuestName())
                    .type(CommentTypeDto.GUEST);
        }
        return builder.build();
    }
}
