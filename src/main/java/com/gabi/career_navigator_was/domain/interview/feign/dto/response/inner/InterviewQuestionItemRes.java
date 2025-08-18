package com.gabi.career_navigator_was.domain.interview.feign.dto.response.inner;

import java.util.List;
import java.util.Map;

import lombok.Builder;

@Builder(toBuilder = true)
public record InterviewQuestionItemRes(
	String id,
	String category,
	Integer difficulty,
	String question,
	List<String> expectedPoints,
	Map<String, Object> evidence,
	Integer position,
	String qHash
) {
}
