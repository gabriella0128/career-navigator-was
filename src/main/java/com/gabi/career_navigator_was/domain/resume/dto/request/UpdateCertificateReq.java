package com.gabi.career_navigator_was.domain.resume.dto.request;

import java.util.List;

import com.gabi.career_navigator_was.domain.resume.dto.request.inner.CertificateReq;

import jakarta.validation.constraints.NotNull;

public record UpdateCertificateReq(
	@NotNull Long resumeIdx,
	List<CertificateReq> certificates
) {
}
