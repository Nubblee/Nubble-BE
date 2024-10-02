package com.nubble.backend.codingproblem.service;

import com.nubble.backend.codingproblem.domain.CodingProblem;
import com.nubble.backend.codingproblem.service.CodingProblemCommand.ProblemCreateCommand;
import com.nubble.backend.codingproblem.service.CodingProblemCommand.ProblemDeleteCommand;
import com.nubble.backend.codingproblem.service.CodingProblemCommand.ProblemSearchCommand;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CodingProblemService {

    private final CodingProblemRepository problemRepository;
    private final UserRepository userRepository;
    private final CodingProblemConditionMapper problemConditionMapper;

    @Transactional
    public Long createProblem(ProblemCreateCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않습니다."));
        CodingProblem newProblem = CodingProblem.builder()
                .quizDate(command.quizDate())
                .title(command.problemTitle())
                .url(command.problemUrl())
                .user(user)
                .build();

        return problemRepository.save(newProblem).getId();
    }

    @Transactional
    public void deleteProblem(ProblemDeleteCommand command) {
        CodingProblem problem = problemRepository.findById(command.problemId())
                .orElseThrow(() -> new EntityNotFoundException("코딩테스트 문제가 존재하지 않습니다."));

        if (!Objects.equals(problem.getUser().getId(), command.userId())) {
            throw new RuntimeException("문제를 등록한 사람만 삭제할 수 있습니다.");
        }
        problemRepository.delete(problem);
    }

    @Transactional
    public List<CodingProblemInfo> searchProblems(ProblemSearchCommand command) {
        ProblemSearchCondition condition = problemConditionMapper.toProblemSearchCondition(command);

        return problemRepository.searchProblems(condition).stream()
                .map(CodingProblemInfo::fromDomain)
                .toList();
    }
}
