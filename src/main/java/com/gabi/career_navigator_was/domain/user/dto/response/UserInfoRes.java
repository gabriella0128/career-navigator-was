package com.gabi.career_navigator_was.domain.user.dto.response;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserInfoRes(
	Long userIdx,
	String userId,
	String userName,
	String userEmail
) {
}
