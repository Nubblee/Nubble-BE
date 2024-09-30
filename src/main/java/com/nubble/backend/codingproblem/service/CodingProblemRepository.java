package com.nubble.backend.codingproblem.service;

import com.nubble.backend.codingproblem.domain.CodingProblem;
import com.nubble.backend.codingproblem.repository.CodingProblemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodingProblemRepository extends JpaRepository<CodingProblem, Long>, CodingProblemRepositoryCustom {

}
