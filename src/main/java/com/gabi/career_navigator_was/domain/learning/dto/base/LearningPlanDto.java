package com.gabi.career_navigator_was.domain.learning.dto.base;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;

@Builder(toBuilder = true)
public record LearningPlanDto(
	Long planIdx,
	Long userIdx,
	LocalDate planDate,
	LocalDate periodStart,
	Long resumeIdx,
	String resumeSnapshot,
	String improvements,
	String seed,
	String modelName,
	String promptVersion,
	LocalDateTime generatedAt
) {
}
