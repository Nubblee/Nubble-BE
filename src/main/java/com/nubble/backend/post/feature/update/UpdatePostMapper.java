package com.nubble.backend.post.feature.update;

import com.nubble.backend.common.BaseMapperConfig;
import com.nubble.backend.post.feature.update.UpdatePostController.UpdatePostRequest;
import com.nubble.backend.post.feature.update.UpdatePostService.UpdatePostCommand;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface UpdatePostMapper {

    UpdatePostCommand toCommand(UpdatePostRequest request, Long postId, long userId);
}
