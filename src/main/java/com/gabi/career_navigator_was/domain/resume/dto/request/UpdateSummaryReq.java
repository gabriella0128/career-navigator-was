package com.gabi.career_navigator_was.domain.resume.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateSummaryReq(
	@NotNull Long resumeIdx,
	String summary
) {
}
