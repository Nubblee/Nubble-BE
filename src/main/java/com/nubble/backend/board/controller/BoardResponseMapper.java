package com.nubble.backend.board.controller;

import com.nubble.backend.board.controller.BoardResponse.PostsDto;
import com.nubble.backend.post.service.PostInfo;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface BoardResponseMapper {

    BoardResponse.PostDto toPostDto(PostInfo.PostDto postDto);

    default BoardResponse.PostsDto toPostsDto(List<PostInfo.PostDto> posts) {
        List<BoardResponse.PostDto> list = posts.stream()
                .map(this::toPostDto)
                .toList();

        return new PostsDto(list);
    }
}
