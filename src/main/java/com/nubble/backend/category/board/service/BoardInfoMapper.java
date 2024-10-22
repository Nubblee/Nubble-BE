package com.nubble.backend.category.board.service;

import com.nubble.backend.category.board.domain.Board;
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

    @Mapping(target = "categoryId", source = "category.id")
    BoardInfo.BoardDto toBoardDto(Board board);
}
