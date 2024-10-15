package com.nubble.backend.board.service;

import com.nubble.backend.board.domain.Board;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface BoardInfoMapper {

    @Mapping(target = "boardId", source = "id")
    @Mapping(target = "boardName", source = "name")
    @Mapping(target = "categoryId", source = "category.id")
    BoardInfo.BoardDto toBoardDto(Board board);
}
