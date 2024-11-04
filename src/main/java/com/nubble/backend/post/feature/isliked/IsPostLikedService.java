package com.nubble.backend.post.feature.isliked;

import com.nubble.backend.post.repository.PostLikeRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IsPostLikedService {

    private final PostLikeRepository postLikeRepository;

    public boolean isPostLiked(IsPostLikedQuery query) {
        return postLikeRepository.existsByPostIdAndUserId(query.postId, query.userId);
    }

    @Builder
    public record IsPostLikedQuery(
            Long postId,
            Long userId
    ) {

    }
}
