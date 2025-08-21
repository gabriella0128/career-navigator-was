package com.gabi.career_navigator_was.domain.resume.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder(toBuilder = true)
public record ResumeListItem(
	Long resumeIdx,
	String title,
	LocalDateTime createDt
) {
}
