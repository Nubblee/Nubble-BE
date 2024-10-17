package com.nubble.backend.post.controller;

import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.interceptor.session.SessionRequired;
import com.nubble.backend.post.controller.PostRequest.PostCreateRequest;
import com.nubble.backend.post.controller.PostResponse.PostCreateResponse;
import com.nubble.backend.post.service.PostCommand.PostCreateCommand;
import com.nubble.backend.post.service.PostCommand.PostUpdateCommand;
import com.nubble.backend.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final PostCommandMapper postCommandMapper;
    private final PostResponseMapper postResponseMapper;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @SessionRequired
    public ResponseEntity<PostCreateResponse> createPost(
            @Valid @RequestBody PostCreateRequest request, UserSession userSession
    ) {
        PostCreateCommand command = postCommandMapper.toPostCreateCommand(request, userSession.userId());
        long newPostId = postService.createPost(command);

        PostCreateResponse response = postResponseMapper.toPostCreateResponse(newPostId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // todo patch /{postId} 로 수정하기
    @PatchMapping(
            path = "/{postId}/publish",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @SessionRequired
    public ResponseEntity<Void> publishPost(
            @Valid @RequestBody PostRequest.PostUpdateRequest request,
            @PathVariable Long postId,
            UserSession userSession
    ) {
        PostUpdateCommand command = postCommandMapper.toPostPublishCommand(request, postId, userSession.userId());
        postService.updatePost(command);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
