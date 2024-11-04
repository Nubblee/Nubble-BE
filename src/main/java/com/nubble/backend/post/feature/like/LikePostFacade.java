package com.nubble.backend.post.feature.like;

import com.nubble.backend.post.feature.like.LikePostService.LikePostCommand;
import com.nubble.backend.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikePostFacade {

    private final LikePostService likePostService;
    private final PostRepository postRepository;

    public Long likePost(LikePostCommand command) {
        Long newPostLikeId = likePostService.likePost(command);
        postRepository.incrementLikeCount(command.postId());

        return newPostLikeId;
    }
}
