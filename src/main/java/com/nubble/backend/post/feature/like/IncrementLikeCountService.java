package com.nubble.backend.post.feature.like;

import com.nubble.backend.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IncrementLikeCountService {

    private final PostRepository postRepository;

    @Transactional
    public void increment(Long postId) {
        postRepository.incrementLikeCount(postId);
    }
}
