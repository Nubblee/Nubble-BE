package com.nubble.backend.codingproblem.controller;

import com.nubble.backend.codingproblem.controller.CodingProblemResponse.ProblemGetResponse;
import com.nubble.backend.codingproblem.controller.CodingProblemResponse.ProblemGetResponses;
import com.nubble.backend.codingproblem.service.CodingProblemInfo;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CodingProblemResponseMapper {

    default ProblemGetResponses toProblemGetResponses(List<CodingProblemInfo> infos) {
        List<ProblemGetResponse> responses = infos.stream()
                .map(this::toProblemGetResponse)
                .toList();

        return new ProblemGetResponses(responses);
    }

    ProblemGetResponse toProblemGetResponse(CodingProblemInfo info);
}
