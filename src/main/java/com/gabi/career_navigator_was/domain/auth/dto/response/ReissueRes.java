package com.gabi.career_navigator_was.domain.auth.dto.response;

import lombok.Builder;

@Builder
public record ReissueRes(
	String accessToken
) {
}
