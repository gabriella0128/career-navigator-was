package com.gabi.career_navigator_was.domain.interview.dto.request;

import jakarta.validation.constraints.NotNull;

public record QuestionDetailReq(
	@NotNull Long questionIdx
) {
}
