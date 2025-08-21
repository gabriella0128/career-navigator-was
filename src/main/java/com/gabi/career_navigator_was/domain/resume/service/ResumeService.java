package com.gabi.career_navigator_was.domain.resume.service;
import java.util.List;

import com.gabi.career_navigator_was.domain.resume.dto.base.ResumeDto;
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
import com.gabi.career_navigator_was.global.dto.CommonResponse;

public interface ResumeService {
	CommonResponse<Void> insertResume(InsertResumeReq body);

	CommonResponse<DetailResumeRes> retrieveResume(DetailResumeReq body);

	CommonResponse<Void> updateTitle(UpdateTitleReq body);

	CommonResponse<Void> updateSummary(UpdateSummaryReq body);

	CommonResponse<Void> updateEducation(UpdateEducationReq body);

	CommonResponse<Void> updateExperience(UpdateExperienceReq body);

	CommonResponse<Void> updateSkill(UpdateSkillReq body);

	CommonResponse<Void> updateCertificate(UpdateCertificateReq body);

	CommonResponse<Void> updateLanguage(UpdateLanguageReq body);

	DetailResumeRes buildDetailResume(ResumeDto resumeDto);

	CommonResponse<Void> changeResume(DetailResumeReq body);

	CommonResponse<List<ResumeListItem>> retrieveResumeList();
}
