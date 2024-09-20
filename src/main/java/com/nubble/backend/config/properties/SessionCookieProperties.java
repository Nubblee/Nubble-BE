package com.nubble.backend.config.properties;

import java.time.Duration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SessionCookieProperties {

    private final String name;
    private final Duration maxAge;
    private final String path;
    private final boolean httpOnly;

    // todo 세션 쿠키 생성시 요구되는 다른 설정들도 추가해야 함
    public SessionCookieProperties(
            @Value("${cookie.session.name:SESSION}") String name,
            @Value("${cookie.session.max-age:30d}") Duration maxAge,
            @Value("${cookie.session.path:/}") String path,
            @Value("${cookie.session.http-only:true}") boolean httpOnly) {
        this.name = name;
        this.maxAge = maxAge;
        this.path = path;
        this.httpOnly = httpOnly;
    }
}
