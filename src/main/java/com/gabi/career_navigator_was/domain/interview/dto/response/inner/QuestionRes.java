package com.gabi.career_navigator_was.domain.interview.dto.response.inner;

import lombok.Builder;

@Builder(toBuilder = true)
public record QuestionRes(
	Long questionIdx,
	Integer position,
	String question,
	Integer difficulty,
	Boolean answered
) {
}
