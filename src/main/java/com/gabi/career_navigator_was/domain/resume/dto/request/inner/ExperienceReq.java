package com.gabi.career_navigator_was.domain.resume.dto.request.inner;

import java.time.LocalDateTime;

public record ExperienceReq(
	Long experienceIdx,
	String companyName,
	String position,
	LocalDateTime startDate,
	LocalDateTime endDate,
	String description
) {
}
