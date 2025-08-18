package com.gabi.career_navigator_was.domain.learning.feign.dto.request;

import java.util.List;

import com.gabi.career_navigator_was.domain.resume.dto.response.DetailResumeRes;

import lombok.Builder;

@Builder(toBuilder = true)
public record LearningFeignReq(
	String sessionUid,
	DetailResumeRes resume,
	List<String> improvements
) {
}
