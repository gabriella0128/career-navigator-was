package com.gabi.career_navigator_was.domain.learning.dto.base;

import lombok.Builder;

@Builder(toBuilder = true)
public record LearningGoalDto(
	Long goalIdx,
	Long planIdx,
	String title,
	String metric,
	String targetValue,
	Integer priority,
	String category
) {
}
