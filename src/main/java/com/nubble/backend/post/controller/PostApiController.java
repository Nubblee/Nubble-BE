package com.nubble.backend.post.controller;

import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.interceptor.session.SessionRequired;
import com.nubble.backend.post.controller.PostRequest.PostCreateRequest;
import com.nubble.backend.post.controller.PostResponse.PostCreateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/posts")
@Controller
public class PostApiController {

    @SessionRequired
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostCreateResponse> createPost(
            @Valid @RequestBody PostCreateRequest request, UserSession userSession) {
        PostCreateResponse response = PostCreateResponse.builder().build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
