package com.gabi.career_navigator_was.domain.user.dto.response;

import lombok.Builder;

@Builder
public record IdCheckRes(
	Boolean isDuplicated
) {
}
