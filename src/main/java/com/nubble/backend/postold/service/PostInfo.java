package com.nubble.backend.postold.service;

import com.nubble.backend.category.board.service.BoardInfo.BoardDto;
import com.nubble.backend.category.service.CategoryInfo.CategoryDto;
import com.nubble.backend.postold.shared.PostStatusDto;
import com.nubble.backend.user.service.UserInfo;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostInfo {

    @Builder
    public record PostDto(
            long id,
            String title,
            String content,
            String thumbnailUrl,
            String description,
            PostStatusDto status,
            LocalDateTime createdAt,
            long userId,
            long boardId) {

    }

    @Builder
    public record PostWithUserDto(
            PostDto post,
            UserInfo.UserDto user
    ) {

    }

    @Builder
    public record PostWithCategoryDto(
            PostWithUserDto postWithUserDto,
            BoardDto board,
            CategoryDto category
    ) {

    }
}
