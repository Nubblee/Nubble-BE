package com.nubble.backend.codingproblem.controller;

import com.nubble.backend.codingproblem.controller.CodingProblemRequest.ProblemCreateRequest;
import com.nubble.backend.codingproblem.controller.CodingProblemResponse.ProblemCreateResponse;
import com.nubble.backend.codingproblem.controller.CodingProblemResponse.ProblemGetResponse;
import com.nubble.backend.codingproblem.controller.CodingProblemResponse.ProblemGetResponses;
import com.nubble.backend.codingproblem.service.CodingProblemService;
import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.interceptor.session.SessionRequired;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coding-problems")
@RequiredArgsConstructor
public class CodingProblemApiController {

    private final CodingProblemService problemService;
    private final CodingProblemCommandMapper problemCommandMapper;

    @SessionRequired
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProblemCreateResponse> createProblem(@Valid @RequestBody ProblemCreateRequest request,
            UserSession userSession) {
        Long problemId = problemService.createProblem(problemCommandMapper.of(request, userSession.userId()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(ProblemCreateResponse.builder()
                        .problemId(problemId)
                        .build());
    }

    @SessionRequired
    @DeleteMapping("/{problemId}")
    public ResponseEntity<Void> deleteProblem(@PathVariable Long problemId, UserSession userSession) {
        problemService.deleteProblem(problemCommandMapper.of(problemId, userSession.userId()));
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<ProblemGetResponses> getAllProblems() {
        ProblemGetResponse problem1 = ProblemGetResponse.builder()
                .problemId(1L)
                .quizDate(LocalDate.now())
                .problemTitle("LV.2 귤 까먹기")
                .build();
        ProblemGetResponse problem2 = ProblemGetResponse.builder()
                .problemId(2L)
                .quizDate(LocalDate.now())
                .problemTitle("LV.2 귤 까먹기")
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(ProblemGetResponses.builder()
                        .problems(List.of(problem1, problem2))
                        .build());
    }
}
