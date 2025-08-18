package com.gabi.career_navigator_was.domain.learning.service;

import com.gabi.career_navigator_was.domain.learning.dto.response.LearningPlanRes;
import com.gabi.career_navigator_was.domain.resume.dto.request.DetailResumeReq;
import com.gabi.career_navigator_was.global.dto.CommonResponse;

public interface LearningService {
	CommonResponse<LearningPlanRes> retrieveLearningPlan();
	CommonResponse<Void> generateLearningPlan();
}
