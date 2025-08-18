package com.gabi.career_navigator_was.domain.interview.dto.base;

import lombok.Builder;

@Builder(toBuilder = true)
public record DailyQuestionDto(
	Long questionIdx,
	Long batchIdx,
	String sessionUid,
	String category,
	Integer difficulty,
	String question,
	String expectedPoints,
	String evidence,
	String qHash,
	Integer position
) {
}
