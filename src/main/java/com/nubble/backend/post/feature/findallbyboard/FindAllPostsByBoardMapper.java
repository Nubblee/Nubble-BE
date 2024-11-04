package com.nubble.backend.post.feature.findallbyboard;

import com.nubble.backend.common.BaseMapperConfig;
import com.nubble.backend.post.feature.PostWithUserDto;
import com.nubble.backend.post.feature.findallbyboard.FindAllPostsByBoardController.FindAllPostsByBoardResponse;
import com.nubble.backend.post.feature.findallbyboard.FindAllPostsByBoardController.FindPostByBoardResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface FindAllPostsByBoardMapper {

    @Mapping(target = "id", source = "post.postId")
    @Mapping(target = "title", source = "post.title")
    @Mapping(target = "thumbnailUrl", source = "post.thumbnailUrl")
    @Mapping(target = "description", source = "post.description")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "createdAt", source = "post.createdAt")
    @Mapping(target = "likeCount", source = "post.likeCount")
    FindPostByBoardResponse toResponse(PostWithUserDto postWithUserDto);

    default FindAllPostsByBoardResponse toResponse(List<PostWithUserDto> postWithUserDtos) {
        List<FindPostByBoardResponse> list = postWithUserDtos.stream()
                .map(this::toResponse)
                .toList();

        return new FindAllPostsByBoardResponse(list);
    }
}
