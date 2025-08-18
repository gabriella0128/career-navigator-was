package com.gabi.career_navigator_was.domain.learning.dto.base;

import lombok.Builder;

@Builder(toBuilder = true)
public record LearningTaskDto(
	Long taskIdx,
	Long goalIdx,
	String taskTitle,
	String resourceUrl,
	Integer weekNo
) {
}
