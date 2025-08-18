package com.gabi.career_navigator_was.domain.learning.dto.response.inner;

import java.util.List;

import lombok.Builder;

@Builder(toBuilder = true)
public record LearningGoalRes(
	Long goalIdx,
	String title,
	String metric,
	String targetValue,
	Integer priority,
	List<LearningTaskRes> learningTasks
) {
}
