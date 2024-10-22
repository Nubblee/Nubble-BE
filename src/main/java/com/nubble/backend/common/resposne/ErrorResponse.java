package com.nubble.backend.common.resposne;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String message;
    private final String errorCode;
    private final LocalDateTime timestamp;

    @Builder
    public ErrorResponse(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
    }
}
