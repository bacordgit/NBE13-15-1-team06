package com.kdt.nbe13151team06;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "seoulDateTimeProvider")
public class JpaAuditingConfig {

    private static final ZoneId SEOUL = ZoneId.of("Asia/Seoul");

    @Bean
    public DateTimeProvider seoulDateTimeProvider() {
        return () -> Optional.of(LocalDateTime.now(SEOUL).withNano(0));
    }
}