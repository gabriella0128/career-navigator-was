package com.gabi.career_navigator_was.domain.resume.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTitleReq(
	@NotNull Long resumeIdx,
	@NotBlank String title
) {
}
