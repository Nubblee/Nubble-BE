package com.nubble.backend.post.controller;

import com.nubble.backend.post.shared.PostStatusDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostRequest {

    public static final int TITLE_MAX_LENGTH = 255;
    public static final int CONTENT_MAX_LENGTH = 255;
    public static final int DESCRIPTION_MAX_LENGTH = 500;
    public static final String TITLE_MAX_LENGTH_MESSAGE = "게시글 제목의 길이는 최대 " + TITLE_MAX_LENGTH + "까지 가능합니다.";
    public static final String CONTENT_MAX_LENGTH_MESSAGE = "게시글 내용의 길이는 최대 " + TITLE_MAX_LENGTH + "까지 가능합니다.";
    public static final String DESCRIPTION_MAX_LENGTH_MESSAGE = "게시글 요약의 길이는 최대 " + DESCRIPTION_MAX_LENGTH + "까지 가능합니다.";

    @Builder
    public record PostCreateRequest(
            @NotBlank
            @Max(value = TITLE_MAX_LENGTH, message = TITLE_MAX_LENGTH_MESSAGE)
            String title,

            @NotBlank
            @Size(max = CONTENT_MAX_LENGTH, message = CONTENT_MAX_LENGTH_MESSAGE)
            String content,

            @NotBlank
            @Min(value = 1, message = "boardId는 최소 1이상 이어야 합니다.")
            Long boardId,

            @NotNull(message = "게시글 상태는 필수입니다.")
            PostStatusDto status,

            String thumbnailUrl,

            @Max(value = DESCRIPTION_MAX_LENGTH, message = DESCRIPTION_MAX_LENGTH_MESSAGE)
            String description) {

    }

    @Builder
    public record PostPublishRequest(
            String thumbnailUrl,
            String description
    ) {

    }
}
