package com.nubble.backend.codingproblem.service;

import java.time.LocalDate;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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

    @EqualsAndHashCode
    @Getter
    public static class ProblemSearchCommand {

        private final LocalDate quizDate;

        @Builder
        private ProblemSearchCommand(LocalDate quizDate) {
            this.quizDate = quizDate;
        }

        public Optional<LocalDate> getQuizDate() {
            return Optional.ofNullable(quizDate);
        }
    }
}
