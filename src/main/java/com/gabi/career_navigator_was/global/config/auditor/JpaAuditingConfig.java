package com.gabi.career_navigator_was.global.config.auditor;

import java.util.Objects;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gabi.career_navigator_was.global.code.HdrType;
import com.gabi.career_navigator_was.global.code.TokenStatus;
import com.gabi.career_navigator_was.global.util.JwtTokenUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfig {
	@Bean
	public AuditorAware<String> auditorAware(AuditorAwareImpl auditorAwareImpl) {
		return auditorAwareImpl;
	}
}
