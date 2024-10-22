package com.nubble.backend.comment.mapper;

import com.nubble.backend.comment.service.CommentQuery.CommentByIdQuery;
import com.nubble.backend.comment.service.CommentQuery.PostByIdQuery;
import com.nubble.backend.comment.service.CommentQuery.UserByIdQuery;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CommentQueryMapper {

    default UserByIdQuery toUserByIdQuery(Long userId) {
        return UserByIdQuery.builder()
                .id(userId).build();
    }

    default PostByIdQuery toPostByIdQuery(Long postId) {
        return PostByIdQuery.builder()
                .id(postId).build();
    }

    default CommentByIdQuery toCommentByIdQuery(Long commentId) {
        return CommentByIdQuery.builder()
                .id(commentId).build();
    }
}
