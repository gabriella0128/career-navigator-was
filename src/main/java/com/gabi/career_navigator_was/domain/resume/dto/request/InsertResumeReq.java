package com.gabi.career_navigator_was.domain.resume.dto.request;

import java.util.List;

import com.gabi.career_navigator_was.domain.resume.dto.request.inner.CertificateReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.inner.EducationReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.inner.ExperienceReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.inner.LanguageReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.inner.SkillReq;

import jakarta.validation.constraints.NotBlank;

public record InsertResumeReq(
	@NotBlank String title,
	String summary,
	List<EducationReq> educations,
	List<ExperienceReq> experiences,
	List<SkillReq> skills,
	List<CertificateReq> certificates,
	List<LanguageReq> languages
) {
}
