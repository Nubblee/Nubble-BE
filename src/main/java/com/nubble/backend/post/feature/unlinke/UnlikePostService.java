package com.nubble.backend.post.feature.unlinke;

import com.nubble.backend.post.domain.PostLike;
import com.nubble.backend.post.repository.PostLikeRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UnlikePostService {

    private final PostLikeRepository postLikeRepository;

    @Transactional
    public void unlikePost(UnlikePostCommand command) {
        PostLike postLike = postLikeRepository.getPostLike(command.postId, command.userId);
        postLikeRepository.delete(postLike);
    }

    @Builder
    public record UnlikePostCommand(
            Long postId,
            Long userId
    ) {
    }
}
