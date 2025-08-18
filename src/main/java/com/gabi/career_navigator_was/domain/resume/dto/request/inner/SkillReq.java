package com.gabi.career_navigator_was.domain.resume.dto.request.inner;

public record SkillReq(
	Long skillIdx,
	String skillName,
	String proficiency
) {
}
