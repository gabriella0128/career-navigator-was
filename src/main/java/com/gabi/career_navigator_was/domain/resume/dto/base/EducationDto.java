package com.gabi.career_navigator_was.domain.resume.dto.base;

import java.time.LocalDateTime;

import com.gabi.career_navigator_was.global.code.YnType;

import lombok.Builder;

@Builder(toBuilder = true)
public record EducationDto(
	Long educationIdx,
	Long resumeIdx,
	String schoolName,
	String degree,
	String major,
	LocalDateTime startDate,
	LocalDateTime endDate,
	String description,
	YnType useYn,
	YnType delYn,
	String createId,
	String modifyId
) {
}
