package com.gabi.career_navigator_was.domain.interview.feign.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder(toBuilder = true)
public record InterviewAnswerFeignRes(
	String sessionUid,
	String seed,
	String modelName,
	String promptVersion,
	LocalDateTime evaluatedAt,
	Integer scoreOverall,
	String scores,
	String feedback,
	String strength,
	String improvements
) {
}
