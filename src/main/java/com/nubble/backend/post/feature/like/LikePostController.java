package com.nubble.backend.post.feature.like;

import com.nubble.backend.config.interceptor.session.SessionRequired;
import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.post.feature.like.LikePostService.LikePostCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikePostController {

    private final LikePostFacade likePostFacade;

    @SessionRequired
    @PutMapping(
            path = "/posts/{postId:\\d+}/likes",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<LikePostResponse> likePost(@PathVariable long postId, UserSession userSession) {
        LikePostCommand command = LikePostCommand.builder()
                .postId(postId)
                .userId(userSession.userId()).build();

        Long newLikeId = likePostFacade.likePost(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new LikePostResponse(newLikeId));
    }

    public record LikePostResponse(
            Long newLikeId
    ) {

    }
}
