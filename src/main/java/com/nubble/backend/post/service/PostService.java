package com.nubble.backend.post.service;

import com.nubble.backend.post.service.PostCommand.PostCreateCommand;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    public long createPost(PostCreateCommand command) {
        return 1L;
    }
}
