package com.gabi.career_navigator_was.domain.dashboard.dto.response.inner;

import java.util.List;

import lombok.Builder;

@Builder(toBuilder = true)
public record Highlights(
	String recentFeedback,
	List<String> topStrength,
	List<String> topImprovements
) {
}
