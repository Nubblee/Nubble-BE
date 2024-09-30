package com.nubble.backend.codingproblem.repository;

import static com.nubble.backend.codingproblem.domain.QCodingProblem.codingProblem;

import com.nubble.backend.codingproblem.domain.CodingProblem;
import com.nubble.backend.codingproblem.service.ProblemSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CodingProblemRepositoryCustomImpl implements CodingProblemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CodingProblem> searchProblems(ProblemSearchCondition condition) {
        return jpaQueryFactory.selectFrom(codingProblem)
                .where(quizDateEq(condition.quizDate()))
                .fetch();
    }

    private BooleanExpression quizDateEq(LocalDate quizDate) {
        return quizDate != null ? codingProblem.quizDate.eq(quizDate) : null;
    }
}
