package com.gabi.career_navigator_was.domain.dashboard.dto.response.inner;

import java.util.List;

import lombok.Builder;

@Builder(toBuilder = true)
public record CalendarBlock(
	String month,
	List<CalendarDay> days
) {
}
