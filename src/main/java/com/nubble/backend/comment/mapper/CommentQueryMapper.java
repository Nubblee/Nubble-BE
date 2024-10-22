package com.nubble.backend.comment.mapper;

import com.nubble.backend.comment.service.CommentQuery.PostByIdQuery;
import com.nubble.backend.comment.service.CommentQuery.UserByIdQuery;
import com.nubble.backend.config.resolver.UserSession;
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
    UserByIdQuery toUserByIdQuery(UserSession userSession);

    @Mapping(target = "id", source = "postId")
    PostByIdQuery toPostByIdQuery(Long postId);
}
