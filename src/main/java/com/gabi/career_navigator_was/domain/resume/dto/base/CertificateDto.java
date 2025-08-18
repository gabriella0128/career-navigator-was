package com.gabi.career_navigator_was.domain.resume.dto.base;

import java.time.LocalDateTime;

import com.gabi.career_navigator_was.global.code.YnType;

import lombok.Builder;

@Builder(toBuilder = true)
public record CertificateDto(
	Long certificateIdx,
	Long resumeIdx,
	String certificateName,
	String issuedBy,
	LocalDateTime issueDate,
	YnType useYn,
	YnType delYn,
	String createId,
	String modifyId
) {
}
