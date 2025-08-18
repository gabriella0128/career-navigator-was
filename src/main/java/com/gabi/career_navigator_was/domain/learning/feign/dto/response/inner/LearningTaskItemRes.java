package com.gabi.career_navigator_was.domain.learning.feign.dto.response.inner;

import lombok.Builder;

@Builder(toBuilder = true)
public record LearningTaskItemRes(
	String taskTitle,
	String resourceUrl,
	Integer weekNo
) {
}
