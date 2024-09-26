package com.nubble.backend.codingproblem.service;

import com.nubble.backend.codingproblem.domain.CodingProblem;
import com.nubble.backend.codingproblem.service.CodingProblemCommand.ProblemCreateCommand;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CodingProblemService {

    private final CodingProblemRepository problemRepository;

    private final UserRepository userRepository;

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
}
