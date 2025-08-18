package com.gabi.career_navigator_was.global.config.auditor;

import org.springframework.stereotype.Component;

import lombok.Getter;
@Component
public class AuditorProvider {
	@Getter
	private static AuditorAwareImpl auditorAware;

	public AuditorProvider(AuditorAwareImpl auditorAware) {
		AuditorProvider.auditorAware = auditorAware;
	}
}
