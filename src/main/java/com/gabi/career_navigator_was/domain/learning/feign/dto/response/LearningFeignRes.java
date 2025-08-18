package com.gabi.career_navigator_was.domain.learning.feign.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.gabi.career_navigator_was.domain.learning.feign.dto.response.inner.LearningGoalItemRes;

import lombok.Builder;

@Builder(toBuilder = true)
public record LearningFeignRes(
	String sessionUid,
	LocalDate planDate,
	String seed,
	String modelName,
	String promptVersion,
	List<LearningGoalItemRes> goals
) {
}
