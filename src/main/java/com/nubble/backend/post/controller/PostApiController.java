package com.nubble.backend.post.controller;

import com.nubble.backend.comment.service.CommentQuery;
import com.nubble.backend.comment.service.member.MemberCommentCommand;
import com.nubble.backend.comment.service.member.MemberCommentCommandService;
import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.interceptor.session.SessionRequired;
import com.nubble.backend.post.controller.PostRequest.PostCreateRequest;
import com.nubble.backend.post.controller.PostResponse.PostCreateResponse;
import com.nubble.backend.post.controller.mapper.CommentQueryMapper;
import com.nubble.backend.post.controller.mapper.MemberCommentCommandMapper;
import com.nubble.backend.post.controller.mapper.PostCommandMapper;
import com.nubble.backend.post.controller.mapper.PostResponseMapper;
import com.nubble.backend.post.service.PostCommand.PostCreateCommand;
import com.nubble.backend.post.service.PostCommand.PostUpdateCommand;
import com.nubble.backend.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    private final PostCommandMapper postCommandMapper;
    private final PostResponseMapper postResponseMapper;
    private final MemberCommentCommandService memberCommentCommandService;
    private final MemberCommentCommandMapper memberCommentCommandMapper;
    private final CommentQueryMapper commentQueryMapper;


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

    @PostMapping(
            path = "/{postId}/comments/member",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @SessionRequired
    public ResponseEntity<PostResponse.MemberCommentCreateResponse> createMemberComment(
            @PathVariable Long postId,
            UserSession userSession,
            @Valid @RequestBody PostRequest.MemberCommentCreateRequest request
    ) {
        CommentQuery.UserByIdQuery userQuery = commentQueryMapper.toUserByIdQuery(userSession);
        MemberCommentCommand.CreateCommand command = memberCommentCommandMapper.toCreateCommand(request);
        CommentQuery.PostByIdQuery postQuery = commentQueryMapper.toPostByIdQuery(postId);
        long newCommentId = memberCommentCommandService.create(userQuery, postQuery, command);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postResponseMapper.toMemberCommentCreateResponse(newCommentId));
    }
}
