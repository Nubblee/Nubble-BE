package com.nubble.backend.postold.service;

import com.nubble.backend.post.domain.Post;
import com.nubble.backend.postold.service.PostInfo.PostWithUserDto;
import com.nubble.backend.userold.domain.User;
import com.nubble.backend.userold.service.UserInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface PostInfoMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "boardId", source = "board.id")
    PostInfo.PostDto toPostDto(Post post);

    default PostInfo.PostWithUserDto toPostWithUserDto(Post post) {
        return PostWithUserDto.builder()
                .post(toPostDto(post))
                .user(toUserDto(post.getUser()))
                .build();
    }

    UserInfo.UserDto toUserDto(User user);
}
