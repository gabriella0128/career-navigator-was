package com.gabi.career_navigator_was.domain.resume.dto.response.inner;

import lombok.Builder;

@Builder
public record LanguageRes(
	Long languageIdx,
	String languageName,
	String level,
	String testName,
	String testScore
) {
}
