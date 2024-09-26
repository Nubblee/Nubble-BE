package com.nubble.backend.codingproblem.service;

import com.nubble.backend.codingproblem.domain.CodingProblem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodingProblemRepository extends JpaRepository<CodingProblem, Long> {

}
