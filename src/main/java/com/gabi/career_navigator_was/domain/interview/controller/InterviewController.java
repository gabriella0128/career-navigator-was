package com.gabi.career_navigator_was.domain.interview.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabi.career_navigator_was.domain.interview.dto.request.AnswerInsertReq;
import com.gabi.career_navigator_was.domain.interview.dto.request.QuestionDetailReq;
import com.gabi.career_navigator_was.domain.interview.dto.response.QuestionDetailRes;
import com.gabi.career_navigator_was.domain.interview.dto.response.inner.QuestionRes;
import com.gabi.career_navigator_was.domain.interview.service.InterviewService;
import com.gabi.career_navigator_was.global.dto.CommonResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interview")
public class InterviewController {
	private final InterviewService interviewService;

	@PostMapping(value = "/generate-daily-question")
	public ResponseEntity<CommonResponse<Void>> generateDailyQuestion() {
		return ResponseEntity.ok(interviewService.generateDailyQuestion());
	}

	@PostMapping(value = "/retrieve-daily-question")
	public ResponseEntity<CommonResponse<List<QuestionRes>>> retrieveDailyQuestion() {
		return ResponseEntity.ok(interviewService.retrieveDailyQuestion());
	}

	@PostMapping(value = "/retrieve-daily-question-detail")
	public ResponseEntity<CommonResponse<QuestionDetailRes>> retrieveDailyQuestionDetail(@RequestBody @Valid
		QuestionDetailReq body) {
		return ResponseEntity.ok(interviewService.retrieveDailyQuestionDetail(body));
	}

	@PostMapping(value = "/generate-daily-question-answer")
	public ResponseEntity<CommonResponse<Void>> generateDailyQuestionAnswer(@RequestBody @Valid AnswerInsertReq body) {
		return ResponseEntity.ok(interviewService.generateDailyQuestionAnswer(body));
	}
}
