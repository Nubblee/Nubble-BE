package com.nubble.backend.comment.mapper;

import com.nubble.backend.comment.service.CommentQuery.CommentByIdQuery;
import com.nubble.backend.comment.service.CommentQuery.PostByIdQuery;
import com.nubble.backend.comment.service.CommentQuery.UserByIdQuery;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CommentQueryMapper {

    @Mapping(target = "id", source = "userId")
    UserByIdQuery toUserByIdQuery(Long userId);

    @Mapping(target = "id", source = "postId")
    PostByIdQuery toPostByIdQuery(Long postId);

    @Mapping(target = "id", source = "commentId")
    CommentByIdQuery toCommentByIdQuery(Long commentId);
}
