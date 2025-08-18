package com.gabi.career_navigator_was.domain.interview.dto.base;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;

@Builder(toBuilder = true)
public record DailyQuestionBatchDto(
	Long batchIdx,
	Long userIdx,
	String sessionUid,
	LocalDate practiceDate,
	Long resumeIdx,
	String resumeSnapshot,
	String seed,
	String modelName,
	String promptVersion,
	LocalDateTime generatedAt
	) {
}
