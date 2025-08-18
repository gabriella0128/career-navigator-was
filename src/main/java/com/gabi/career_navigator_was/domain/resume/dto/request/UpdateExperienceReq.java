package com.gabi.career_navigator_was.domain.resume.dto.request;

import java.util.List;

import com.gabi.career_navigator_was.domain.resume.dto.request.inner.ExperienceReq;

import jakarta.validation.constraints.NotNull;

public record UpdateExperienceReq(
	@NotNull Long resumeIdx,
	List<ExperienceReq> experiences
) {
}
