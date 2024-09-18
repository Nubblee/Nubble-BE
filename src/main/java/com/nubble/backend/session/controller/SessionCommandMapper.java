package com.nubble.backend.session.controller;

import com.nubble.backend.session.service.SessionCommand.SessionCreationCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface SessionCommandMapper {
    SessionCreationCommand fromRequest(SessionRequest.Issuance request);
}
