package com.nubble.backend.codingproblem.domain;

import com.nubble.backend.common.BaseEntity;
import com.nubble.backend.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coding_problems")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CodingProblem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate quizDate;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public CodingProblem(LocalDate quizDate, String title, String url, User user) {
        this.quizDate = quizDate;
        this.title = title;
        this.url = url;
        this.user = user;
    }
}
