package com.nubble.backend.post.mapper;

import com.nubble.backend.post.comment.service.CommentInfo;
import com.nubble.backend.post.controller.PostResponse;
import com.nubble.backend.post.controller.PostResponse.CommentCreateResponse;
import com.nubble.backend.post.controller.PostResponse.CommentResponse;
import com.nubble.backend.post.controller.PostResponse.CommentResponses;
import com.nubble.backend.post.controller.PostResponse.PostCreateResponse;
import com.nubble.backend.post.service.PostInfo.PostWithUserDto;
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

    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "createdAt", source = "post.createdAt")
    @Mapping(target = "title", source = "post.title")
    @Mapping(target = "content", source = "post.content")
    @Mapping(target = "thumbnailUrl", source = "post.thumbnailUrl")
    @Mapping(target = "postStatus", source = "post.status")
    @Mapping(target = "userId", source = "post.userId")
    @Mapping(target = "userNickname", source = "user.nickname")
    PostResponse.PostDetailResponse toPostDetailResponse(PostWithUserDto dto);
}
