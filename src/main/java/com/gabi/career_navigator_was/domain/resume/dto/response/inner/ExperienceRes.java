package com.gabi.career_navigator_was.domain.resume.dto.response.inner;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ExperienceRes(
	Long experienceIdx,
	String companyName,
	String position,
	LocalDateTime startDate,
	LocalDateTime endDate,
	String description
) {
}
