package com.nubble.backend.post.mapper;

import com.nubble.backend.post.controller.PostResponse;
import com.nubble.backend.post.controller.PostResponse.PostCreateResponse;
import com.nubble.backend.post.service.PostInfo.PostWithCategoryDto;
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

    @Mapping(target = "postId", source = "postWithUserDto.post.id")
    @Mapping(target = "createdAt", source = "postWithUserDto.post.createdAt")
    @Mapping(target = "title", source = "postWithUserDto.post.title")
    @Mapping(target = "content", source = "postWithUserDto.post.content")
    @Mapping(target = "thumbnailUrl", source = "postWithUserDto.post.thumbnailUrl")
    @Mapping(target = "postStatus", source = "postWithUserDto.post.status")
    @Mapping(target = "userId", source = "postWithUserDto.post.userId")
    @Mapping(target = "userNickname", source = "postWithUserDto.user.nickname")
    @Mapping(target = "boardId", source = "board.id")
    @Mapping(target = "boardName", source = "board.name")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    PostResponse.PostDetailResponse toPostDetailResponse(PostWithCategoryDto dto);
}
