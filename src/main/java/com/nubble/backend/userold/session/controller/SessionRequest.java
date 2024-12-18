package com.nubble.backend.userold.session.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionRequest {

    @Builder
    public record SessionIssueRequest(
            @NotBlank(message = "유저 아이디는 필수입니다.")
            @Size(min = 3, max = 50, message = "잘못된 유저 아이디 길이입니다.")
            String username,

            @NotBlank(message = "비밀번호는 필수입니다.")
            @Size(min = 4, max = 128, message = "잘못된 비밀번호 길이입니다.")
            String password
    ) {

    }
}
