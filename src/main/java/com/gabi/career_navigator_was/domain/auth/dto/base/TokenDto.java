package com.gabi.career_navigator_was.domain.auth.dto.base;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder(toBuilder = true)
public record TokenDto(
	String userId,
	String refreshToken,
	LocalDateTime createdAt,
	Long expiration
) {
}
