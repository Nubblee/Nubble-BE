package com.nubble.backend.comment.service.guest;

import com.nubble.backend.comment.service.CommentQuery.PostByIdQuery;
import com.nubble.backend.comment.service.guest.GuestCommentCommand.CreateCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GuestCommentCommandService {

    @Transactional
    public long create(
            PostByIdQuery postQuery,
            CreateCommand createCommand) {
        // 게스트 댓글을 생성한다.
        // 게시글과 댓글을 매핑한다.
        // 댓글을 저장한다.
        return 0;
    }
}
