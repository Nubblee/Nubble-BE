package com.nubble.backend.post.exception;

public class PostNotPublishedException extends RuntimeException {

    public PostNotPublishedException() {
        super("댓글을 입력할 수 없습니다. 해당 포스트가 게시되지 않았습니다.");
    }
}
