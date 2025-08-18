package com.gabi.career_navigator_was.domain.resume.dto.request;

import java.util.List;

import com.gabi.career_navigator_was.domain.resume.dto.request.inner.LanguageReq;

import jakarta.validation.constraints.NotNull;

public record UpdateLanguageReq(
	@NotNull Long resumeIdx,
	List<LanguageReq> languages
) {
}
