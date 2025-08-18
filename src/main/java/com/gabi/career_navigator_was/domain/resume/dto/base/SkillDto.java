package com.gabi.career_navigator_was.domain.resume.dto.base;

import com.gabi.career_navigator_was.global.code.YnType;

import lombok.Builder;

@Builder(toBuilder = true)
public record SkillDto(
	Long skillIdx,
	Long resumeIdx,
	String skillName,
	String proficiency,
	YnType useYn,
	YnType delYn,
	String createId,
	String modifyId
) {
}
