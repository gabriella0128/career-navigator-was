package com.gabi.career_navigator_was.domain.interview.dto.base;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder(toBuilder = true)
public record DailyAnswerEvalDto(
	Long evalIdx,
	Long answerIdx,
	Long userIdx,
	Integer scoreOverall,
	String scores,
	String feedback,
	String strength,
	String improvements,
	String seed,
	String modelName,
	String promptVersion,
	LocalDateTime evaluatedAt
) {
}
