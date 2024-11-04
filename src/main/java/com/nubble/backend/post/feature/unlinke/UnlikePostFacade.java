package com.nubble.backend.post.feature.unlinke;

import com.nubble.backend.post.feature.unlinke.UnlikePostService.UnlikePostCommand;
import com.nubble.backend.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UnlikePostFacade {

    private final UnlikePostService unlikePostService;
    private final PostRepository postRepository;

    @Transactional
    public void unlikePost(UnlikePostCommand command) {
        unlikePostService.unlikePost(command);
        postRepository.decrementLikeCount(command.postId());
    }
}