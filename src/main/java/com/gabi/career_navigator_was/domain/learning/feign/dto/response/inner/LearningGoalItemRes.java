package com.gabi.career_navigator_was.domain.learning.feign.dto.response.inner;

import java.util.List;

import lombok.Builder;

@Builder(toBuilder = true)
public record LearningGoalItemRes(
	String title,
	String metric,
	String targetValue,
	Integer priority,
	String category,
	List<LearningTaskItemRes> tasks
) {
}
