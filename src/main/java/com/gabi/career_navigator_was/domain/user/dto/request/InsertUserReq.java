package com.gabi.career_navigator_was.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record InsertUserReq(
	@NotBlank String userId,
	@NotBlank String userPasswd,
	@NotBlank String userName,
	@NotBlank String userEmail
) {
}
