package com.nubble.backend.codingproblem.controller;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodingProblemRequest {

    @Builder
    public record ProblemCreateRequest(
            @DateTimeFormat(iso = ISO.DATE)
            LocalDate quizDate,

            @NotBlank
            String problemTitle,

            @URL(protocol = "https")
            String problemUrl
    ) {

    }
}
