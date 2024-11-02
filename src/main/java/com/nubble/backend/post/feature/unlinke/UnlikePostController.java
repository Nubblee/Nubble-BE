package com.nubble.backend.post.feature.unlinke;

import com.nubble.backend.config.interceptor.session.SessionRequired;
import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.post.feature.like.LikePostController.LikePostResponse;
import com.nubble.backend.post.feature.unlinke.UnlikePostService.UnlikePostCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UnlikePostController {

    private final UnlikePostService unlikePostService;

    @SessionRequired
    @DeleteMapping(path = "/posts/{postId:\\d+}/likes")
    public ResponseEntity<LikePostResponse> unlikePost(@PathVariable long postId, UserSession userSession) {
        UnlikePostCommand command = UnlikePostCommand.builder()
                .postId(postId)
                .userId(userSession.userId()).build();

        unlikePostService.unlikePost(command);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
