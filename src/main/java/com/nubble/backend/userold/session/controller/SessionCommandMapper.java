package com.nubble.backend.userold.session.controller;

import com.nubble.backend.userold.session.controller.SessionRequest.SessionIssueRequest;
import com.nubble.backend.userold.session.service.SessionCommand.SessionCreateCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface SessionCommandMapper {

    @Mapping(target = "username", source = "username")
    SessionCreateCommand fromRequest(SessionIssueRequest request);
}
