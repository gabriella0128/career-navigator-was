package com.gabi.career_navigator_was.domain.resume.dto.response.inner;

import lombok.Builder;

@Builder
public record SkillRes(
	Long skillIdx,
	String skillName,
	String proficiency
) {
}
