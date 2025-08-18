package com.gabi.career_navigator_was.domain.resume.dto.request;

import java.util.List;

import com.gabi.career_navigator_was.domain.resume.dto.request.inner.EducationReq;

import jakarta.validation.constraints.NotNull;

public record UpdateEducationReq(
	@NotNull Long resumeIdx,
	List<EducationReq> educations
) {
}
