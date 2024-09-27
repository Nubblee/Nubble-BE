package com.nubble.backend.codingproblem.service;

import static com.nubble.backend.codingproblem.service.CodingProblemCommand.ProblemCreateCommand;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.nubble.backend.codingproblem.service.CodingProblemCommand.ProblemDeleteCommand;
import com.nubble.backend.fixture.UserFixture;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CodingProblemServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CodingProblemService problemService;

    @Autowired
    private CodingProblemRepository problemRepository;

    @DisplayName("코딩 테스트 문제를 생성합니다.")
    @Test
    void createProblem_success() {
        // given
        User user = UserFixture.aUser().build();
        userRepository.save(user);

        ProblemCreateCommand command = ProblemCreateCommand.builder()
                .quizDate(LocalDate.now())
                .problemTitle("할로윈의 양아치")
                .problemUrl("https://www.acmicpc.net/problem/20303")
                .userId(user.getId())
                .build();

        // when
        Long newProblemId = problemService.createProblem(command);

        // then
        Assertions.assertThat(problemRepository.findById(newProblemId)).isPresent();
    }

    @DisplayName("문제를 등록한 사람은 문제를 삭제할 수 있습니다.")
    @Test
    void deleteProblem() {
        // given
        User user = UserFixture.aUser().build();
        userRepository.save(user);

        Long problemId = problemService.createProblem(ProblemCreateCommand.builder()
                .quizDate(LocalDate.now())
                .problemTitle("할로윈의 양아치")
                .problemUrl("https://www.acmicpc.net/problem/20303")
                .userId(user.getId())
                .build());

        ProblemDeleteCommand command = ProblemDeleteCommand.builder()
                .problemId(problemId)
                .userId(user.getId())
                .build();

        // when & then
        assertThatCode(() -> problemService.deleteProblem(command))
                .doesNotThrowAnyException();
    }
}
