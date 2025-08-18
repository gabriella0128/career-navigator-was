package com.gabi.career_navigator_was.domain.interview.feign.dto.request;

import lombok.Builder;

@Builder(toBuilder = true)
public record InterviewAnswerFeignReq(
	String sessionUid,
	String question,
	String answer
) {
}
