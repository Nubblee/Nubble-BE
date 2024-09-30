package com.nubble.backend.codingproblem.repository;

import com.nubble.backend.codingproblem.domain.CodingProblem;
import com.nubble.backend.codingproblem.service.ProblemSearchCondition;
import java.util.List;

public interface CodingProblemRepositoryCustom {

    List<CodingProblem> searchProblems(ProblemSearchCondition condition);
}
