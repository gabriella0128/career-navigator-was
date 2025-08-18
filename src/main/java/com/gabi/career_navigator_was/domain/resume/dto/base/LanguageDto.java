package com.gabi.career_navigator_was.domain.resume.dto.base;

import com.gabi.career_navigator_was.global.code.YnType;

import lombok.Builder;

@Builder(toBuilder = true)
public record LanguageDto(
	Long languageIdx,
	Long resumeIdx,
	String languageName,
	String level,
	String testName,
	String testScore,
	YnType useYn,
	YnType delYn,
	String createId,
	String modifyId
) {
}
