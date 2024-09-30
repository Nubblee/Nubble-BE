package com.nubble.backend.codingproblem.controller;

import com.nubble.backend.codingproblem.controller.CodingProblemRequest.ProblemCreateRequest;
import com.nubble.backend.codingproblem.controller.CodingProblemRequest.ProblemSearchRequest;
import com.nubble.backend.codingproblem.controller.CodingProblemResponse.ProblemCreateResponse;
import com.nubble.backend.codingproblem.controller.CodingProblemResponse.ProblemSearchResponse;
import com.nubble.backend.codingproblem.service.CodingProblemInfo;
import com.nubble.backend.codingproblem.service.CodingProblemService;
import com.nubble.backend.config.resolver.UserSession;
import com.nubble.backend.interceptor.session.SessionRequired;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private final CodingProblemResponseMapper problemResponseMapper;

    @SessionRequired
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProblemCreateResponse> createProblem(@Valid @RequestBody ProblemCreateRequest request,
            UserSession userSession) {
        Long problemId = problemService.createProblem(problemCommandMapper.of(request, userSession.userId()));

        return ResponseEntity.status(HttpStatus.CREATED)
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
    public ResponseEntity<ProblemSearchResponse> searchProblems(@Valid @ModelAttribute ProblemSearchRequest request) {
        List<CodingProblemInfo> infos = problemService.findAllProblems();

        return ResponseEntity.status(HttpStatus.OK)
                .body(problemResponseMapper.toProblemSearchResponse(infos));
    }
}
