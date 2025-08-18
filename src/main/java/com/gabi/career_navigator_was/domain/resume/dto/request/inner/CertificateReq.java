package com.gabi.career_navigator_was.domain.resume.dto.request.inner;

import java.time.LocalDateTime;

public record CertificateReq(
	Long certificateIdx,
	String certificateName,
	String issuedBy,
	LocalDateTime issueDate
) {
}
