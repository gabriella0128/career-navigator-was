package com.gabi.career_navigator_was.domain.resume.dto.base;

import java.time.LocalDateTime;

import com.gabi.career_navigator_was.global.code.YnType;

import lombok.Builder;

@Builder(toBuilder = true)
public record ExperienceDto(
	Long experienceIdx,
	Long resumeIdx,
	String companyName,
	String position,
	LocalDateTime startDate,
	LocalDateTime endDate,
	String description,
	YnType useYn,
	YnType delYn,
	String createId,
	String modifyId
) {
}
