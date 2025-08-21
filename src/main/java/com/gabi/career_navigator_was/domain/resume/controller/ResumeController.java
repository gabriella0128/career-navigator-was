package com.gabi.career_navigator_was.domain.resume.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabi.career_navigator_was.domain.resume.dto.request.DetailResumeReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.InsertResumeReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.UpdateCertificateReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.UpdateEducationReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.UpdateExperienceReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.UpdateLanguageReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.UpdateSkillReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.UpdateSummaryReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.UpdateTitleReq;
import com.gabi.career_navigator_was.domain.resume.dto.response.DetailResumeRes;
import com.gabi.career_navigator_was.domain.resume.dto.response.ResumeListItem;
import com.gabi.career_navigator_was.domain.resume.service.ResumeService;
import com.gabi.career_navigator_was.global.dto.CommonResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resume")
public class ResumeController {

	private final ResumeService resumeService;

	@PostMapping(value = "/insert-resume")
	public ResponseEntity<CommonResponse<Void>> registerResume(@RequestBody @Valid InsertResumeReq body) {
		return ResponseEntity.ok(resumeService.insertResume(body));
	}

	@PostMapping(value = "/retrieve-resume")
	public ResponseEntity<CommonResponse<DetailResumeRes>> retrieveResume(@RequestBody @Valid DetailResumeReq body) {
		return ResponseEntity.ok(resumeService.retrieveResume(body));
	}

	@PostMapping(value = "/update-title")
	public ResponseEntity<CommonResponse<Void>> updateTitle(@RequestBody @Valid UpdateTitleReq body) {
		return ResponseEntity.ok(resumeService.updateTitle(body));
	}

	@PostMapping(value = "/update-summary")
	public ResponseEntity<CommonResponse<Void>> updateSummary(@RequestBody @Valid UpdateSummaryReq body) {
		return ResponseEntity.ok(resumeService.updateSummary(body));
	}

	@PostMapping(value = "/update-education")
	public ResponseEntity<CommonResponse<Void>> updateEducation(@RequestBody @Valid UpdateEducationReq body) {
		return ResponseEntity.ok(resumeService.updateEducation(body));
	}

	@PostMapping(value = "/update-experience")
	public ResponseEntity<CommonResponse<Void>> updateExperience(@RequestBody @Valid UpdateExperienceReq body) {
		return ResponseEntity.ok(resumeService.updateExperience(body));
	}

	@PostMapping(value = "/update-skill")
	public ResponseEntity<CommonResponse<Void>> updateSkill(@RequestBody @Valid UpdateSkillReq body) {
		return ResponseEntity.ok(resumeService.updateSkill(body));
	}

	@PostMapping(value = "/update-certificate")
	public ResponseEntity<CommonResponse<Void>> updateCertificate(@RequestBody @Valid UpdateCertificateReq body) {
		return ResponseEntity.ok(resumeService.updateCertificate(body));
	}

	@PostMapping(value = "/update-language")
	public ResponseEntity<CommonResponse<Void>> updateLanguage(@RequestBody @Valid UpdateLanguageReq body) {
		return ResponseEntity.ok(resumeService.updateLanguage(body));
	}

	@PostMapping(value = "/change-resume")
	public ResponseEntity<CommonResponse<Void>> changeResume(@RequestBody @Valid DetailResumeReq body) {
		return ResponseEntity.ok(resumeService.changeResume(body));
	}

	@PostMapping(value = "/retrieve-all-resume-list")
	public ResponseEntity<CommonResponse<List<ResumeListItem>>> retrieveResumeList() {
		return ResponseEntity.ok(resumeService.retrieveResumeList());
	}
}
