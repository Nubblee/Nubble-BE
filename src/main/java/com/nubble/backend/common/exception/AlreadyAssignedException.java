package com.nubble.backend.common.exception;

public class AlreadyAssignedException extends RuntimeException {

    public AlreadyAssignedException() {
        super("이미 다른 객체에 할당된 상태입니다.");
    }

    public AlreadyAssignedException(String message) {
        super(message);
    }
}
