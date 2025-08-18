package com.gabi.career_navigator_was.domain.learning.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabi.career_navigator_was.domain.interview.service.InterviewService;
import com.gabi.career_navigator_was.domain.learning.dto.response.LearningPlanRes;
import com.gabi.career_navigator_was.domain.learning.service.LearningService;
import com.gabi.career_navigator_was.global.dto.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/learning")
public class LearningController {
	private final LearningService learningService;

	@PostMapping(value = "/retrieve-learning-plan")
	public ResponseEntity<CommonResponse<LearningPlanRes>> retrieveLearningPlan() {
		return ResponseEntity.ok(learningService.retrieveLearningPlan());
	}

	@PostMapping(value = "/generate-learning-plan")
	public ResponseEntity<CommonResponse<Void>> generateLearningPlan() {
		return ResponseEntity.ok(learningService.generateLearningPlan());
	}
}
