package com.nubble.backend.codingproblem.service;

import com.nubble.backend.codingproblem.domain.CodingProblem;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record CodingProblemInfo(
        long problemId,
        LocalDate quizDate,
        String problemTitle,
        String url
) {

    public static CodingProblemInfo fromDomain(CodingProblem problem) {
        return CodingProblemInfo.builder()
                .problemId(problem.getId())
                .quizDate(problem.getQuizDate())
                .problemTitle(problem.getTitle())
                .url(problem.getUrl())
                .build();
    }
}
