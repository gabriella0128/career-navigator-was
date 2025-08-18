package com.gabi.career_navigator_was.domain.learning.dto.response;

import java.util.List;

import com.gabi.career_navigator_was.domain.learning.dto.response.inner.LearningGoalRes;

import lombok.Builder;

@Builder(toBuilder = true)
public record LearningPlanRes(
	Integer planMonth,
	Long planIdx,
	List<LearningGoalRes> learningGoals
) {
}
