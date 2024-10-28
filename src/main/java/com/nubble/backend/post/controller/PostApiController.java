package com.nubble.backend.post.controller;

import com.nubble.backend.comment.domain.CommentInfo;
import com.nubble.backend.config.interceptor.session.SessionRequired;
import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.post.comment.service.CommentService;
import com.nubble.backend.post.controller.PostRequest.PostCreateRequest;
import com.nubble.backend.post.controller.PostResponse.CommentsResponse;
import com.nubble.backend.post.controller.PostResponse.PostCreateResponse;
import com.nubble.backend.post.mapper.PostCommandMapper;
import com.nubble.backend.post.mapper.PostResponseMapper;
import com.nubble.backend.post.service.PostCommand.PostCreateCommand;
import com.nubble.backend.post.service.PostCommand.PostUpdateCommand;
import com.nubble.backend.post.service.PostFacade;
import com.nubble.backend.post.service.PostInfo.PostWithCategoryDto;
import com.nubble.backend.post.service.PostService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final CommentService commentService;
    private final PostCommandMapper postCommandMapper;
    private final PostResponseMapper postResponseMapper;
    private final PostFacade postFacade;


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
            path = "/{postId}/comments",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentsResponse> findAllCommentsByPostId(
            @PathVariable Long postId) {
        List<CommentInfo.CommentDto> comments = commentService.findAllByPostId(postId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(postResponseMapper.toCommentsResponse(comments));
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
