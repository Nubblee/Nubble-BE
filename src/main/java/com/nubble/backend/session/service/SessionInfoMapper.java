package com.nubble.backend.session.service;

import com.nubble.backend.session.domain.Session;
import com.nubble.backend.session.service.SessionInfo.SessionCreateInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface SessionInfoMapper {

    @Mapping(target = "sessionId", source = "accessId")
    @Mapping(target = "userId", source = "user.id")
    SessionCreateInfo toSessionCreateInfo(Session session);
}
