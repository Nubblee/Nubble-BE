package com.nubble.backend.common.exception;

public class NoAuthorizationException extends RuntimeException {

    public NoAuthorizationException() {
        super("권한이 없습니다.");
    }

    public NoAuthorizationException(String message) {
        super(message);
    }
}
