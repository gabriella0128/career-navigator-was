package com.gabi.career_navigator_was.domain.resume.dto.request;

import java.util.List;

import com.gabi.career_navigator_was.domain.resume.dto.request.inner.SkillReq;

import jakarta.validation.constraints.NotNull;

public record UpdateSkillReq(
	@NotNull Long resumeIdx,
	List<SkillReq> skills
) {
}
