package com.nubble.backend.codingproblem.service;

import static com.nubble.backend.codingproblem.service.CodingProblemCommand.ProblemCreateCommand;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.nubble.backend.codingproblem.service.CodingProblemCommand.ProblemDeleteCommand;
import com.nubble.backend.codingproblem.service.CodingProblemCommand.ProblemSearchCommand;
import com.nubble.backend.fixture.domain.UserFixture;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import java.time.LocalDate;
import java.util.List;
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
        assertThat(problemRepository.findById(newProblemId)).isPresent();
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

    @DisplayName("매개변수의 모든 값이 null인 경우, 모든 코딩테스트 문제를 조회합니다.")
    @Test
    void searchProblems_shouldReturnAllProblems_whenSearchCommandIsEmpty() {
        // given
        for (int ui = 1; ui <= 2; ui++) {
            User user = UserFixture.aUser()
                    .withUsername("user%d".formatted(ui))
                    .build();
            userRepository.save(user);

            for (int pi = 1; pi <= 5; pi++) {
                ProblemCreateCommand command = ProblemCreateCommand.builder()
                        .userId(user.getId())
                        .quizDate(LocalDate.now())
                        .problemTitle("%s가 만든 %d번째 문제".formatted(user.getNickname(), pi))
                        .problemUrl("https://www.acmicpc.net/problem/20303")
                        .build();
                problemService.createProblem(command);
            }
        }

        ProblemSearchCommand command = ProblemSearchCommand.builder()
                .build();

        // when
        List<CodingProblemInfo> actualAllProblems = problemService.searchProblems(command);

        // then
        assertThat(actualAllProblems).hasSize(10);
    }

    @DisplayName("퀴즈 날짜가 명시되어 있을 경우, 해당 날짜의 문제만 가져옵니다.")
    @Test
    void searchProblems_shouldReturnProblemsForSpecificDate_whenQuizDateIsProvided() {
        // given
        for (int ui = 1; ui <= 2; ui++) {
            User user = UserFixture.aUser()
                    .withUsername("user%d".formatted(ui))
                    .build();
            userRepository.save(user);

            for (int pi = 1; pi <= 5; pi++) {
                ProblemCreateCommand command = ProblemCreateCommand.builder()
                        .userId(user.getId())
                        .quizDate(LocalDate.now().plusDays(pi))
                        .problemTitle("%s가 만든 %d번째 문제".formatted(user.getNickname(), pi))
                        .problemUrl("https://www.acmicpc.net/problem/20303")
                        .build();
                problemService.createProblem(command);
            }
        }

        LocalDate targetQuizDate = LocalDate.now().plusDays(1);
        ProblemSearchCommand command = ProblemSearchCommand.builder()
                .quizDate(targetQuizDate)
                .build();

        // when
        List<CodingProblemInfo> actual = problemService.searchProblems(command);

        // then
        assertThat(actual).hasSize(2);
        assertThat(actual.getFirst().quizDate()).isEqualTo(targetQuizDate);
    }
}
