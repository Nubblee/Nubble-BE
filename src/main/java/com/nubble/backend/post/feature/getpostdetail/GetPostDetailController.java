package com.nubble.backend.post.feature.getpostdetail;

import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.post.domain.PostStatus;
import com.nubble.backend.post.feature.getpostdetail.GetPostDetailFacade.GetPostDetailFacadeInfo;
import com.nubble.backend.post.feature.getpostdetail.GetPostWithUserService.GetPostWithUserQuery;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetPostDetailController {

    private final GetPostDetailFacade getPostDetailFacade;
    private final GetPostDetailMapper mapper;

    @GetMapping(
            path = "/posts/{postId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GetPostDetailResponse> getPostDetail(
            @PathVariable Long postId, UserSession userSession) {
        GetPostWithUserQuery query = mapper.toQuery(userSession, postId);
        GetPostDetailFacadeInfo result = getPostDetailFacade.getPostDetailById(query);

        return ResponseEntity.status(HttpStatus.OK)
                .body(mapper.toResponse(result));
    }

    public record GetPostDetailResponse(
            Long postId,
            LocalDateTime createdAt,
            String title,
            String content,
            String thumbnailUrl,
            PostStatus postStatus,
            Long userId,
            String userNickname,
            Long boardId,
            String boardName,
            Long categoryId,
            String categoryName,
            int likeCount
    ) {

    }
}
