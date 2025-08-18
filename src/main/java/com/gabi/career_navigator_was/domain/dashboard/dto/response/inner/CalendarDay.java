package com.gabi.career_navigator_was.domain.dashboard.dto.response.inner;

import lombok.Builder;

@Builder(toBuilder = true)
public record CalendarDay(
	String date,
	Integer count
) {
}
