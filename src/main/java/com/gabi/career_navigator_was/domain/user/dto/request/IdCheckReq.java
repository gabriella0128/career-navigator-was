package com.gabi.career_navigator_was.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record IdCheckReq(
	@NotBlank
	@Pattern(
		regexp = "^[a-z0-9]{8,20}$",
		message = "사용자 ID는 8~20자의 영문 소문자와 숫자로만 이루어져야 합니다."
	)
	String userId
) {
}
