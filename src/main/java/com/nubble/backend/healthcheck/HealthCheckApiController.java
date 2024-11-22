package com.nubble.backend.healthcheck;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckApiController {

    @GetMapping("/healthcheck")
    public ResponseEntity<HealthCheckResponse> healthCheck() {
        HealthCheckResponse response = new HealthCheckResponse("ok", "Hello world!!");
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    public record HealthCheckResponse(
            String status,
            String message) {
    }
}
