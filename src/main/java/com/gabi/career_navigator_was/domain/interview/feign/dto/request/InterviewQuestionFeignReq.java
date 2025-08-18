package com.gabi.career_navigator_was.domain.interview.feign.dto.request;

import java.util.List;

import com.gabi.career_navigator_was.domain.resume.dto.response.DetailResumeRes;

import lombok.Builder;

@Builder(toBuilder = true)
public record InterviewQuestionFeignReq(
	String sessionUid,
	DetailResumeRes resume,
	List<String> recentQuestions
) {
}
