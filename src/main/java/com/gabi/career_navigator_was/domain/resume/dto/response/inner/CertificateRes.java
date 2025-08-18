package com.gabi.career_navigator_was.domain.resume.dto.response.inner;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record CertificateRes(
	Long certificateIdx,
	String certificateName,
	String issuedBy,
	LocalDateTime issueDate
) {
}
