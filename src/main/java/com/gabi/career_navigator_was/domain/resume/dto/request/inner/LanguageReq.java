package com.gabi.career_navigator_was.domain.resume.dto.request.inner;

public record LanguageReq(
	Long languageIdx,
	String languageName,
	String level,
	String testName,
	String testScore
) {
}
