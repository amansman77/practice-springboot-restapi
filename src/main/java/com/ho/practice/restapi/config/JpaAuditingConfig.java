package com.ho.practice.restapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "commonAuditorAware")
@Configuration
public class JpaAuditingConfig {

}