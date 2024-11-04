package com.nubble.backend.post.feature.unlinke;

import com.nubble.backend.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DecrementLikeCountService {

    private final PostRepository postRepository;

    @Transactional
    public void decrement(Long postId) {
        postRepository.decrementLikeCount(postId);
    }
}
