package com.gabi.career_navigator_was.domain.user.dto.base;

import java.time.LocalDateTime;

import com.gabi.career_navigator_was.global.code.YnType;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserInfoDto(
	Long userIdx,
	String userId,
	String userPasswd,
	String userName,
	String userEmail,
	LocalDateTime lastLoginDt,

	YnType useYn,
	YnType delYn,
	String createId,
	String modifyId
) {
}
