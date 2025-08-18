package com.gabi.career_navigator_was.domain.learning.dto.response.inner;

import lombok.Builder;

@Builder(toBuilder = true)
public record LearningTaskRes(
	Long taskIdx,
	Long goalIdx,
	String taskTitle,
	String resourceUrl,
	Integer weekNo
) {
}
