package com.gabi.career_navigator_was.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginReq(
	@NotBlank String userId, // 사용자 아이디
	@NotBlank String userPasswd
) {
}
