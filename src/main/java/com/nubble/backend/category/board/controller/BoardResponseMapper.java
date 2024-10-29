package com.nubble.backend.category.board.controller;

import com.nubble.backend.category.board.controller.BoardResponse.PostsWithUserResponse;
import com.nubble.backend.postold.service.PostInfo;
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
public interface BoardResponseMapper {

    @Mapping(target = "id", source = "post.id")
    @Mapping(target = "title", source = "post.title")
    @Mapping(target = "thumbnailUrl", source = "post.thumbnailUrl")
    @Mapping(target = "description", source = "post.description")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "createdAt", source = "post.createdAt")
    BoardResponse.PostWithUserResponse toPostWithUserResponse(PostInfo.PostWithUserDto postWithUser);

    default BoardResponse.PostsWithUserResponse toPostsWithUserResponse(List<PostInfo.PostWithUserDto> postsWIthUser) {
        List<BoardResponse.PostWithUserResponse> list = postsWIthUser.stream()
                .map(this::toPostWithUserResponse)
                .toList();

        return PostsWithUserResponse.builder()
                .posts(list).build();
    }
}
