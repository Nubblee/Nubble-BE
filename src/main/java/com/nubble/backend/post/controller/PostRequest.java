package com.nubble.backend.post.controller;

import com.nubble.backend.post.shared.PostStatusDto;
import jakarta.annotation.Nullable;
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
    public static final int THUMBNAIL_MAX_LENGTH = 255;
    public static final int COMMENT_MAX_LENGTH = 1000;
    public static final String TITLE_MAX_LENGTH_MESSAGE = "게시글 제목의 길이는 최대 " + TITLE_MAX_LENGTH + "까지 가능합니다.";
    public static final String CONTENT_MAX_LENGTH_MESSAGE = "게시글 내용의 길이는 최대 " + TITLE_MAX_LENGTH + "까지 가능합니다.";
    public static final String DESCRIPTION_MAX_LENGTH_MESSAGE = "게시글 요약의 길이는 최대 " + DESCRIPTION_MAX_LENGTH + "까지 가능합니다.";
    public static final String THUMBNAIL_MAX_LENGTH_MESSAGE = "썸네일 URL의 길이는 최대 " + THUMBNAIL_MAX_LENGTH + "까지 가능합니다.";
    public static final String COMMENT_MAX_LENGTH_MESSAGE = "코멘트의 길이는 최대 " + COMMENT_MAX_LENGTH + "까지 가능합니다.";

    @Builder
    public record PostCreateRequest(
            @NotBlank(message = "게시글 제목은 비어있을 수 없습니다.")
            @Size(max = TITLE_MAX_LENGTH, message = TITLE_MAX_LENGTH_MESSAGE)
            String title,

            @NotBlank(message = "게시글 내용은 비어있을 수 없습니다.")
            @Size(max = CONTENT_MAX_LENGTH, message = CONTENT_MAX_LENGTH_MESSAGE)
            String content,

            @NotNull(message = "게시판 아이디는 null일 수 없습니다.")
            @Min(value = 1, message = "boardId는 최소 1이상 이어야 합니다.")
            Long boardId,

            @NotNull(message = "게시글 상태는 null일 수 없습니다.")
            PostStatusDto status,

            String thumbnailUrl,

            @Size(max = DESCRIPTION_MAX_LENGTH, message = DESCRIPTION_MAX_LENGTH_MESSAGE)
            String description) {

    }

    @Builder
    public record PostUpdateRequest(
            @NotBlank(message = "게시글 제목은 비어있을 수 없습니다.")
            @Size(max = TITLE_MAX_LENGTH, message = TITLE_MAX_LENGTH_MESSAGE)
            String title,

            @NotBlank(message = "게시글 내용은 비어있을 수 없습니다.")
            @Size(max = CONTENT_MAX_LENGTH, message = CONTENT_MAX_LENGTH_MESSAGE)
            String content,

            @NotNull(message = "게시판 아이디는 null일 수 없습니다.")
            @Min(value = 1, message = "boardId는 최소 1이상 이어야 합니다.")
            Long boardId,

            @NotNull(message = "게시글 상태는 null일 수 없습니다.")
            PostStatusDto status,

            @Nullable
            @Size(max =THUMBNAIL_MAX_LENGTH, message = THUMBNAIL_MAX_LENGTH_MESSAGE)
            String thumbnailUrl,

            @Nullable
            @Size(max = DESCRIPTION_MAX_LENGTH, message = DESCRIPTION_MAX_LENGTH_MESSAGE)
            String description
    ) {

    }

    @Builder
    public record MemberCommentCreateRequest(
            @NotBlank(message = "댓글 내용은 비어있을 수 없습니다.")
            @Size(max = COMMENT_MAX_LENGTH, message = COMMENT_MAX_LENGTH_MESSAGE)
            String content
    ) {

    }

    @Builder
    public record GuestCommentCreateRequest(
            @NotBlank(message = "댓글 내용은 비어있을 수 없습니다.")
            @Size(max = COMMENT_MAX_LENGTH, message = COMMENT_MAX_LENGTH_MESSAGE)
            String content,

            @NotBlank
            String guestName,

            @NotBlank
            String guestPassword
    ) {

    }
}
