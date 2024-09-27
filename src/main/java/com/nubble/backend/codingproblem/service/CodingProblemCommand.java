package com.nubble.backend.codingproblem.service;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodingProblemCommand {

    @Builder
    public record ProblemCreateCommand(
            Long userId,
            LocalDate quizDate,
            String problemTitle,
            String problemUrl) {
        
    }

    @Builder
    public record ProblemDeleteCommand(
            Long userId,
            Long problemId) {

    }
}
