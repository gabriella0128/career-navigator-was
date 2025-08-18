package com.gabi.career_navigator_was.domain.resume.dto.response.inner;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record EducationRes(
	Long educationIdx,
	String schoolName,
	String degree,
	String major,
	LocalDateTime startDate,
	LocalDateTime endDate,
	String description
) {
}
