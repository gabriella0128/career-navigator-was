package com.gabi.career_navigator_was.domain.interview.dto.response;

import lombok.Builder;

@Builder(toBuilder = true)
public record QuestionDetailRes(
	Long questionIdx,
	String question,
	Integer position,
	String answer,
	Integer scoreOverall,
	String scores,
	String feedback,
	String strength,
	String improvements
) {
}
