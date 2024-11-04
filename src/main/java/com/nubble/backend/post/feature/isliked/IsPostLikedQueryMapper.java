package com.nubble.backend.post.feature.isliked;

import com.nubble.backend.common.BaseMapperConfig;
import com.nubble.backend.post.feature.isliked.IsPostLikedService.IsPostLikedQuery;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface IsPostLikedQueryMapper {

    IsPostLikedQuery toQuery(Long postId, Long userId);
}
