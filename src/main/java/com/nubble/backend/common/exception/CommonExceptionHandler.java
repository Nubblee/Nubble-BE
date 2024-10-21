package com.nubble.backend.common.exception;

import com.nubble.backend.common.resposne.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(AlreadyAssignedException.class)
    public ResponseEntity<ErrorResponse> handle(AlreadyAssignedException exception) {
        ErrorResponse error = ErrorResponse.builder()
                .errorCode("ALREADY_ASSIGNED")
                .message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }
}
