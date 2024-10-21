package com.nubble.backend.post.exception;

import com.nubble.backend.common.resposne.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostExceptionHandler {

    @ExceptionHandler(PostNotPublishedException.class)
    public ResponseEntity<ErrorResponse> handlePostNotPublishedException(PostNotPublishedException exception) {
        ErrorResponse error = ErrorResponse.builder()
                .errorCode("POST_NOT_PUBLISHED")
                .message(exception.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
