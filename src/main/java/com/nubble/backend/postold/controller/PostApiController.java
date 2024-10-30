package com.nubble.backend.postold.controller;

import com.nubble.backend.config.interceptor.session.SessionRequired;
import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.postold.mapper.PostCommandMapper;
import com.nubble.backend.postold.mapper.PostResponseMapper;
import com.nubble.backend.postold.service.PostCommand.PostUpdateCommand;
import com.nubble.backend.postold.service.PostFacade;
import com.nubble.backend.postold.service.PostInfo.PostWithCategoryDto;
import com.nubble.backend.postold.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final PostCommandMapper postCommandMapper;
    private final PostResponseMapper postResponseMapper;
    private final PostFacade postFacade;

    @PutMapping(
            path = "/{postId}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @SessionRequired
    public ResponseEntity<Void> updatePost(
            @Valid @RequestBody PostRequest.PostUpdateRequest request,
            @PathVariable Long postId,
            UserSession userSession
    ) {
        PostUpdateCommand command = postCommandMapper.toPostUpdateCommand(request, postId, userSession.userId());
        postService.updatePost(command);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping(
            path = "/{postId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PostResponse.PostDetailResponse> getPost(@PathVariable Long postId) {
        PostWithCategoryDto post = postFacade.getPostById(postId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(postResponseMapper.toPostDetailResponse(post));
    }
}
