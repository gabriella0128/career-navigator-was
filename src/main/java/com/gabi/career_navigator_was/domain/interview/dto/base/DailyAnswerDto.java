package com.gabi.career_navigator_was.domain.interview.dto.base;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder(toBuilder = true)
public record DailyAnswerDto(
	Long answerIdx,
	Long questionIdx,
	Long userIdx,
	String answer,
	LocalDateTime submittedAt
) {
}
