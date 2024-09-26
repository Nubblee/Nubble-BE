package com.nubble.backend.codingproblem.controller;

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
}
