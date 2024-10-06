package com.nubble.backend.post.service;

import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.service.PostCommand.PostCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public long createPost(PostCreateCommand command) {
        Post newPost = Post.builder()
                .title(command.title())
                .content(command.content())
                .build();

        return postRepository.save(newPost)
                .getId();
    }
}
