package com.nubble.backend.codingproblem.controller;

import com.nubble.backend.codingproblem.controller.CodingProblemRequest.ProblemCreateRequest;
import com.nubble.backend.codingproblem.service.CodingProblemCommand.ProblemCreateCommand;
import com.nubble.backend.codingproblem.service.CodingProblemCommand.ProblemDeleteCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CodingProblemCommandMapper {

    // todo of 메소드명에 대해서 생각해보기, of이고 같은 매개인자가 들어오지만, 다른 객체 반환을 원하면 문제 발생
    ProblemCreateCommand of(ProblemCreateRequest request, long userId);

    ProblemDeleteCommand of(Long problemId, long userId);
}
