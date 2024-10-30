package com.nubble.backend.post.feature.create;

import com.nubble.backend.common.BaseMapperConfig;
import com.nubble.backend.post.feature.create.CreatePostController.CreatePostRequest;
import com.nubble.backend.post.feature.create.CreatePostService.CreatePostCommand;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface CreatePostMapper {

    CreatePostCommand toCommand(CreatePostRequest request, Long userId);
}
