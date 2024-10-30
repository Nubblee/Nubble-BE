package com.nubble.backend.post.feature.getpostdetail;

import com.nubble.backend.common.BaseMapperConfig;
import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.post.feature.getpostdetail.GetPostDetailController.GetPostDetailResponse;
import com.nubble.backend.post.feature.getpostdetail.GetPostDetailFacade.GetPostDetailFacadeResult;
import com.nubble.backend.post.feature.getpostdetail.GetPostWithUserService.GetPostWithUserQuery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface GetPostDetailMapper {

    @Mapping(target = "postId", source = "post.postId")
    @Mapping(target = "createdAt", source = "post.createdAt")
    @Mapping(target = "title", source = "post.title")
    @Mapping(target = "content", source = "post.content")
    @Mapping(target = "thumbnailUrl", source = "post.thumbnailUrl")
    @Mapping(target = "postStatus", source = "post.status")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userNickname", source = "user.nickname")
    @Mapping(target = "boardId", source = "board.id")
    @Mapping(target = "boardName", source = "board.name")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    GetPostDetailResponse toResponse(GetPostDetailFacadeResult result);

    GetPostWithUserQuery toQuery(UserSession userSession, Long postId);
}
