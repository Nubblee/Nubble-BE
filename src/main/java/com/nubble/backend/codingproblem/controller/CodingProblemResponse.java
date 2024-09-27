package com.nubble.backend.codingproblem.controller;

import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodingProblemResponse {

    @Builder
    public record ProblemCreateResponse(
            long problemId
    ) {

    }

    @Builder
    public record ProblemGetResponse(
            long problemId,
            LocalDate quizDate,
            String problemTitle
    ) {

    }

    @Builder
    public record ProblemGetResponses(
            List<ProblemGetResponse> problems
    ) {

    }
}
