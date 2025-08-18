package com.gabi.career_navigator_was.domain.resume.dto.request.inner;

import java.time.LocalDateTime;

public record EducationReq(
	Long educationIdx,
	String schoolName,
	String degree,
	String major,
	LocalDateTime startDate,
	LocalDateTime endDate,
	String description
) {
}
