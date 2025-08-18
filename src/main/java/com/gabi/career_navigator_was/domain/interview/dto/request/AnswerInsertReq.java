package com.gabi.career_navigator_was.domain.interview.dto.request;

import jakarta.validation.constraints.NotNull;

public record AnswerInsertReq(
	@NotNull Long questionIdx,
	@NotNull String answer
) {
}
