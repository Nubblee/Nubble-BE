package com.nubble.backend.post.comment.controller;

import com.nubble.backend.post.comment.controller.CommentResponse.CommentCreateResponse;
import com.nubble.backend.post.comment.controller.CommentResponse.CommentFindResponse;
import com.nubble.backend.post.comment.controller.CommentResponse.CommentFindResponses;
import com.nubble.backend.post.comment.service.CommentInfo;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CommentResponseMapper {


    CommentCreateResponse toCommentCreateResponse(Long commentId);

    @Mapping(target = "commentType", source = "type")
    CommentFindResponse toCommentFindResponse(CommentInfo commentInfo);

    default CommentFindResponses toCommentFindResponses(List<CommentInfo> commentInfos) {
        List<CommentFindResponse> responses = commentInfos.stream()
                .map(this::toCommentFindResponse)
                .toList();

        return new CommentFindResponses(responses);
    }
}
