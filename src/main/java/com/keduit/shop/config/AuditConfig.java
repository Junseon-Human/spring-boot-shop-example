package com.keduit.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
// JPA Auditing 기능을 활성화
@EnableJpaAuditing
public class AuditConfig {
    
    @Bean
    public AuditorAware<String> auditorProvider() {
        // 등록자, 수정자를 처리해주는 AuditorAware를 Bean 으로 등록
        return new AuditorAwareImpl();
    }
}
