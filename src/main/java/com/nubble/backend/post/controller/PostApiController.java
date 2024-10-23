package com.nubble.backend.post.controller;

import com.nubble.backend.post.comment.mapper.CommentQueryMapper;
import com.nubble.backend.post.comment.mapper.GuestCommentCommandMapper;
import com.nubble.backend.post.comment.mapper.MemberCommentCommandMapper;
import com.nubble.backend.post.comment.service.CommentInfo;
import com.nubble.backend.post.comment.service.CommentQuery;
import com.nubble.backend.post.comment.service.CommentService;
import com.nubble.backend.post.comment.service.guest.GuestCommentCommand.CreateCommand;
import com.nubble.backend.post.comment.service.guest.GuestCommentCommandService;
import com.nubble.backend.post.comment.service.member.MemberCommentCommand;
import com.nubble.backend.post.comment.service.member.MemberCommentCommandService;
import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.config.interceptor.session.SessionRequired;
import com.nubble.backend.post.controller.PostRequest.PostCreateRequest;
import com.nubble.backend.post.controller.PostResponse.CommentCreateResponse;
import com.nubble.backend.post.controller.PostResponse.PostCreateResponse;
import com.nubble.backend.post.mapper.PostCommandMapper;
import com.nubble.backend.post.mapper.PostResponseMapper;
import com.nubble.backend.post.service.PostCommand.PostCreateCommand;
import com.nubble.backend.post.service.PostCommand.PostUpdateCommand;
import com.nubble.backend.post.service.PostInfo.PostWithUserDto;
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
    private final MemberCommentCommandService memberCommentCommandService;
    private final GuestCommentCommandService guestCommentCommandService;
    private final CommentService commentService;
    private final PostCommandMapper postCommandMapper;
    private final PostResponseMapper postResponseMapper;
    private final MemberCommentCommandMapper memberCommentCommandMapper;
    private final CommentQueryMapper commentQueryMapper;
    private final GuestCommentCommandMapper guestCommentCommandMapper;


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
    public ResponseEntity<CommentCreateResponse> createMemberComment(
            @PathVariable Long postId,
            UserSession userSession,
            @Valid @RequestBody PostRequest.MemberCommentCreateRequest request
    ) {
        CommentQuery.UserByIdQuery userQuery = commentQueryMapper.toUserByIdQuery(userSession.userId());
        CommentQuery.PostByIdQuery postQuery = commentQueryMapper.toPostByIdQuery(postId);
        MemberCommentCommand.CreateCommand command = memberCommentCommandMapper.toCreateCommand(request);
        long newCommentId = memberCommentCommandService.createMemberComment(userQuery, postQuery, command);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postResponseMapper.toCommentCreateResponse(newCommentId));
    }

    @PostMapping(
            path = "/{postId}/comments/guest",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponse.CommentCreateResponse> createGuestComment(
            @PathVariable Long postId,
            @Valid @RequestBody PostRequest.GuestCommentCreateRequest request
    ) {
        CommentQuery.PostByIdQuery postQuery = commentQueryMapper.toPostByIdQuery(postId);
        CreateCommand command = guestCommentCommandMapper.toCreateCommand(request);
        long newCommentId = guestCommentCommandService.create(postQuery, command);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postResponseMapper.toCommentCreateResponse(newCommentId));
    }

    @GetMapping(
            path = "/{postId}/comments",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponse.CommentResponses> findAllCommentsByPostId(
            @PathVariable Long postId) {
        List<CommentInfo.CommentDto> comments = commentService.findAllByPostId(postId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(postResponseMapper.toCommentResponses(comments));
    }

    @GetMapping(
            path = "/{postId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PostResponse.PostDetailResponse> getPost(@PathVariable Long postId) {
        PostWithUserDto post = postService.getPostById(postId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(postResponseMapper.toPostDetailResponse(post));
    }
}
