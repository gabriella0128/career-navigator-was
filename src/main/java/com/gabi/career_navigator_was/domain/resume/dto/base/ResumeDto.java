package com.gabi.career_navigator_was.domain.resume.dto.base;

import java.time.LocalDateTime;

import com.gabi.career_navigator_was.global.code.YnType;

import lombok.Builder;

@Builder(toBuilder = true)
public record ResumeDto(
	Long resumeIdx,
	Long userIdx,
	String title,
	String summary,
	YnType representYn,
	YnType useYn,
	YnType delYn,
	String createId,
	String modifyId,
	LocalDateTime createDt
) {
}
