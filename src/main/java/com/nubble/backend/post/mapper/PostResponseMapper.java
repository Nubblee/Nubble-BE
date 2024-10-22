package com.nubble.backend.post.mapper;

import com.nubble.backend.comment.service.CommentInfo;
import com.nubble.backend.post.controller.PostResponse.CommentCreateResponse;
import com.nubble.backend.post.controller.PostResponse.CommentResponse;
import com.nubble.backend.post.controller.PostResponse.CommentResponses;
import com.nubble.backend.post.controller.PostResponse.PostCreateResponse;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface PostResponseMapper {

    PostCreateResponse toPostCreateResponse(Long postId);

    CommentCreateResponse toCommentCreateResponse(Long newCommentId);

    CommentResponse toCommentResponse(CommentInfo.CommentDto comments);

    default CommentResponses toCommentResponses(List<CommentInfo.CommentDto> infos) {
        List<CommentResponse> comments = infos.stream()
                .map(this::toCommentResponse)
                .toList();

        return CommentResponses.builder()
                .comments(comments).build();
    }
}
