package com.gabi.career_navigator_was.domain.resume.dto.response;

import java.util.List;

import com.gabi.career_navigator_was.domain.resume.dto.response.inner.CertificateRes;
import com.gabi.career_navigator_was.domain.resume.dto.response.inner.EducationRes;
import com.gabi.career_navigator_was.domain.resume.dto.response.inner.ExperienceRes;
import com.gabi.career_navigator_was.domain.resume.dto.response.inner.LanguageRes;
import com.gabi.career_navigator_was.domain.resume.dto.response.inner.SkillRes;

import lombok.Builder;

@Builder
public record DetailResumeRes(
	Long resumeIdx,
	String title,
	String summary,
	List<EducationRes> educations,
	List<ExperienceRes> experiences,
	List<SkillRes> skills,
	List<CertificateRes> certificates,
	List<LanguageRes> languages
) {
}
