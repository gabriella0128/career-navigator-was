package com.gabi.career_navigator_was.domain.interview.feign.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.gabi.career_navigator_was.domain.interview.feign.dto.response.inner.InterviewQuestionItemRes;

import lombok.Builder;

@Builder(toBuilder = true)
public record InterviewQuestionFeignRes(
	String sessionUid,
	String seed,
	String modelName,
	String promptVersion,
	LocalDate practiceDate,
	List<InterviewQuestionItemRes> questions
) {
}
