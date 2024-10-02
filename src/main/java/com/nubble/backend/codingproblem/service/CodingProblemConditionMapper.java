package com.nubble.backend.codingproblem.service;

import com.nubble.backend.codingproblem.service.CodingProblemCommand.ProblemSearchCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CodingProblemConditionMapper {

    @Mapping(target = "quizDate", expression = "java(command.getQuizDate().orElse(null))")
    ProblemSearchCondition toProblemSearchCondition(ProblemSearchCommand command);
}
